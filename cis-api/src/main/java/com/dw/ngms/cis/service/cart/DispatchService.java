package com.dw.ngms.cis.service.cart;

import com.dw.ngms.cis.connector.FtpService;
import com.dw.ngms.cis.enums.ProcessTemplateType;
import com.dw.ngms.cis.enums.StorageContext;
import com.dw.ngms.cis.persistence.domains.User;
import com.dw.ngms.cis.persistence.domains.cart.*;
import com.dw.ngms.cis.persistence.domains.workflow.Workflow;
import com.dw.ngms.cis.persistence.domains.workflow.WorkflowDocuments;
import com.dw.ngms.cis.persistence.projection.cart.CartDispatchDocView;
import com.dw.ngms.cis.persistence.repository.cart.CartDispatchRepository;
import com.dw.ngms.cis.persistence.repository.cart.CartItemRepository;
import com.dw.ngms.cis.persistence.repository.cart.CartItemsDispatchDocsRepository;
import com.dw.ngms.cis.persistence.repository.cart.CartRepository;
import com.dw.ngms.cis.persistence.repository.workflow.WorkflowDocumentRepository;
import com.dw.ngms.cis.persistence.repository.workflow.WorkflowRepository;
import com.dw.ngms.cis.pubsub.AppEventPublisher;
import com.dw.ngms.cis.service.FileStorageService;
import com.dw.ngms.cis.service.UserService;
import com.dw.ngms.cis.service.dto.cart.CartDispatchDto;
import com.dw.ngms.cis.service.dto.cart.CartDispatchItemDto;
import com.dw.ngms.cis.service.dto.workflow.DispatchEmailNotificationDto;
import com.dw.ngms.cis.service.event.EventType;
import com.dw.ngms.cis.service.mapper.CartDispatchMapper;
import com.dw.ngms.cis.service.mapper.CartItemsMapper;
import com.dw.ngms.cis.service.workflow.ProcessNotification;
import com.dw.ngms.cis.service.workflow.ProcessNotificationFactory;
import com.dw.ngms.cis.utilities.CartUtils;
import com.dw.ngms.cis.utilities.FileUtils;
import com.dw.ngms.cis.web.vm.cart.CartItemDispatchVm;
import com.dw.ngms.cis.web.vm.cart.DispatchDocumentUploadVm;
import com.dw.ngms.cis.web.vm.workflow.ProcessNotificationsVm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author : prateekgoel
 * @since : 11/02/21, Thu
 **/
@Service
@AllArgsConstructor
@Slf4j
public class DispatchService {

    private final CartItemRepository cartItemRepository;
    private final CartItemsDispatchDocsRepository cartItemsDispatchDocsRepository;
    private final WorkflowDocumentRepository workflowDocumentRepository;
    private final FileStorageService fileStorageService;
    private final CartRepository cartRepository;
    private final ObjectMapper mapper;
    private final CartItemsMapper cartItemsMapper;
    private final CartDispatchRepository cartDispatchRepository;
    private final CartDispatchMapper cartDispatchMapper;
    private final CartUtils cartUtils;
    private final ProcessNotificationFactory processNotificationFactory;

    /**
     * @param dispatchDocumentUploadVm {@link DispatchDocumentUploadVm}
     */
    public void addSupportingDocs(DispatchDocumentUploadVm dispatchDocumentUploadVm) {

        // save document in workflow_document
        WorkflowDocuments workflowDocuments = WorkflowDocuments.builder()
                .workflowId(dispatchDocumentUploadVm.getWorkflowId())
                .notes(dispatchDocumentUploadVm.getNotes())
                .userId(dispatchDocumentUploadVm.getUserId())
                .extension(FileUtils.getFileExtension(dispatchDocumentUploadVm.getFile().getOriginalFilename()))
                .uploadedOn(new Date())
                .uploadRefId(dispatchDocumentUploadVm.getCartItemId())
                .documentTypeId(dispatchDocumentUploadVm.getDocumentTypeId())
                .sizeKb(dispatchDocumentUploadVm.getFile().getSize() / 1024)
                .build();
        WorkflowDocuments savedDoc = workflowDocumentRepository.save(workflowDocuments);

        // save file into storage system
        MultipartFile file = dispatchDocumentUploadVm.getFile();
        String storageLocation = StorageContext.WORKFLOW.getStorageContext() +
                FileUtils.PATH_SEPARATOR +
                savedDoc.getDocumentId();
        log.debug("storing file at location [{}]", storageLocation);
        final String storedDocument = fileStorageService.storeFile(file, storageLocation);

        savedDoc.setDocName(storedDocument);
        workflowDocumentRepository.save(savedDoc);


        // save uploaded document information in cart_item_dispatch_doc
        CartItemsDispatchDocs cartItemsDispatchDocs = new CartItemsDispatchDocs();
        cartItemsDispatchDocs.setCartItemsId(dispatchDocumentUploadVm.getCartItemId());
        cartItemsDispatchDocs.setDocumentId(savedDoc.getDocumentId());
        cartItemsDispatchDocs.setNotes(dispatchDocumentUploadVm.getNotes());
        cartItemsDispatchDocs.setWorkflowId(dispatchDocumentUploadVm.getWorkflowId());
        cartItemsDispatchDocsRepository.save(cartItemsDispatchDocs);

    }

    /**
     * @param documentId documentId
     * @return {@link Resource}
     */
    public Resource getCartItemDocs(final Long documentId) {
        final WorkflowDocuments workflowDocumentsByDocumentId = workflowDocumentRepository
                .findWorkflowDocumentsByDocumentId(documentId);
        String location = StorageContext.WORKFLOW.getStorageContext() +
                File.separator +
                workflowDocumentsByDocumentId.getDocumentId() +
                FileUtils.EXTENSION_SEPARATOR +
                workflowDocumentsByDocumentId.getExtension();
        log.debug("loading cartItemDocs from location {}", location);
        return fileStorageService.loadFileAsResource(location);
    }

    /**
     * @param documentId documentId
     * @return documentName
     */
    public String getCartItemDocumentName(final Long documentId) {
        WorkflowDocuments workflowDocuments = workflowDocumentRepository
                .findWorkflowDocumentsByDocumentId(documentId);
        return workflowDocuments.getDocName();

    }

    /**
     * @param cartItemId cartItemId
     * @param pageable   {@link Pageable}
     * @return {@link com.dw.ngms.cis.persistence.projection.WorkflowDocuments}
     */
    public Page<com.dw.ngms.cis.persistence.projection.WorkflowDocuments>
    getCartItemDocuments(Long cartItemId, Pageable pageable) {
        return workflowDocumentRepository.getCartItemDocs(cartItemId, pageable);
    }

    /**
     * @param cartItemDispatchVm {@link CartItemDispatchVm}
     * @return true/false
     */
    public Boolean addDispatchInformation(CartItemDispatchVm cartItemDispatchVm) {
        Optional<CartItems> cartItems = cartItemRepository.findById(cartItemDispatchVm.getCartItemId());
        if (cartItems.isPresent()) {
            CartItems cartItems1 = cartItems.get();
            cartItems1.setDispatchComment(cartItemDispatchVm.getComments());
            cartItems1.setDispatchStatus(cartItemDispatchVm.getDispatchStatus());
            cartItemRepository.save(cartItems1);
            return true;
        }
        return false;
    }

    public Collection<CartDispatchItemDto> generateDispatchData(Long workflowId) {

        Cart byWorkflowId = cartRepository.findByWorkflowId(workflowId);
        Set<CartData> cartData = byWorkflowId.getCartData();

        Collection<CartItems> allByWorkflowId = cartItemRepository.findAllByWorkflowIdOrderBySno(workflowId);

        final CartDispatchAdditionalInfo cartDispatchAdditionalInfo = cartUtils.cartDispatchAdditionalInfo(workflowId);

        return allByWorkflowId
                .stream()
                .collect(Collectors.groupingBy(CartItems::getCartDataId))
                .entrySet()
                .stream()
                .map(longListEntry -> {
                    CartDispatchItemDto cartDispatchItemDto = new CartDispatchItemDto();
                    cartDispatchItemDto.setCartDispatchAdditionalInfo(cartDispatchAdditionalInfo);
                    Long cartDataId = longListEntry.getKey();
                    Optional<CartData> first = cartData.stream()
                            .filter(cartData1 -> cartData1.getId().equals(cartDataId))
                            .findFirst();
                    if (first.isPresent()) {
                        String jsonData = first.get().getJsonData();
                        try {

                            CartJsonData cartJsonData = mapper.readerFor(CartJsonData.class)
                                    .readValue(jsonData);

                            cartDispatchItemDto.setSearchDetails(cartJsonData.getSearchDetails());
                            cartDispatchItemDto.setTemplateListItemId(cartJsonData.getTemplateListItemId());
                            cartDispatchItemDto.setCartItems(longListEntry.getValue()
                                    .stream()
                                    .map(cartItemsMapper::cartItemsToCartItemDispatchDto)
                                    .collect(Collectors.toCollection(LinkedList::new)));

                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                            log.error(" error occurred while parsing cart json data with cause {}", e.getMessage());
                        }

                    } else if (cartDataId == -1) {

                        cartDispatchItemDto.setCartItems(longListEntry.getValue()
                                .stream()
                                .map(cartItemsMapper::cartItemsToCartItemDispatchDto)
                                .collect(Collectors.toCollection(LinkedList::new)));

                    }
                    return cartDispatchItemDto;
                }).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * @param cartDispatchDto {@link CartDispatchDto}
     * @return {@link CartDispatchDto}
     */
    public CartDispatchDto addCartDispatchInformation(final CartDispatchDto cartDispatchDto) {
        CartDispatch cartDispatch = cartDispatchMapper.cartDispatchDtoToCartDispatch(cartDispatchDto);
        CartDispatch savedCartDispatch = cartDispatchRepository.save(cartDispatch);
        return cartDispatchMapper.cartDispatchToCartDispatchDto(savedCartDispatch);
    }

    /**
     * @param workflowId workflowId
     * @return CartDispatchDocView {@link CartDispatchDocView}
     */
    public CartDispatchDocView getAllDispatchDocsForWorkflow(final Long workflowId) {
        return cartItemsDispatchDocsRepository.getCartDispatchDocuments(workflowId);
    }

    /**
     * @param workflowId      workflowId
     * @param zipOutputStream zipOutputStream
     */
    public void downloadZippedDispatchDocs(final Long workflowId, ZipOutputStream zipOutputStream) {

        cartUtils.zippedDispatchedDocument(workflowId, zipOutputStream);
    }

    /**
     * method to add dispatch document to ftp server.
     *
     * @param workflowId workflowId
     */
    @Async
    public void uploadDocsOnFtp(final Long workflowId) {
        cartUtils.uploadDispatchDocsOnFtp(workflowId);
    }

    public void sendEmailNotificationForDispatchItems(final Long workflowId) throws Exception {
        Cart byWorkflowId = cartRepository.findByWorkflowId(workflowId);
        if (null != byWorkflowId) {

            //final RequesterInformation requesterInformation = cartUtils.getRequesterInformation(workflowId);
            final Requester requesterDetails = cartUtils.getRequesterDetails(workflowId);

            ProcessNotificationsVm processNotificationsVm = new ProcessNotificationsVm();
            processNotificationsVm.setEmail(requesterDetails.getEmail());
            processNotificationsVm.setTemplateId(17L);
            processNotificationsVm.setFullName(requesterDetails.getFirstName());
            processNotificationsVm.setWorkflowId(workflowId);

            final ProcessNotification processNotification = processNotificationFactory
                    .getProcessNotification(ProcessTemplateType.of(processNotificationsVm.getTemplateId()))
                    .orElseThrow(() -> new IllegalArgumentException("Wrong Process Template Type"));

            processNotification.process(processNotificationsVm);

            //appEventPublisher.publishEvent(dispatchEmailNotificationDto, EventType.DISPATCH);
        }
    }
}
