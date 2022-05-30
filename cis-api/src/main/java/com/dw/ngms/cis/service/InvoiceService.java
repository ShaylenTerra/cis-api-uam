package com.dw.ngms.cis.service;

import com.dw.ngms.cis.cisemailer.service.EmailService;
import com.dw.ngms.cis.cisemailer.util.MailUtils;
import com.dw.ngms.cis.enums.StorageContext;
import com.dw.ngms.cis.enums.SystemConfigurationType;
import com.dw.ngms.cis.exception.ResourceNotFoundException;
import com.dw.ngms.cis.persistence.domains.Payment;
import com.dw.ngms.cis.persistence.domains.Template;
import com.dw.ngms.cis.persistence.domains.cart.*;
import com.dw.ngms.cis.persistence.domains.system.SystemConfiguration;
import com.dw.ngms.cis.persistence.domains.workflow.WorkflowDocuments;
import com.dw.ngms.cis.persistence.projection.fee.InvoiceItemCostProjection;
import com.dw.ngms.cis.persistence.repository.PaymentRepository;
import com.dw.ngms.cis.persistence.repository.TemplateRepository;
import com.dw.ngms.cis.persistence.repository.cart.CartItemRepository;
import com.dw.ngms.cis.persistence.repository.cart.CartRepository;
import com.dw.ngms.cis.persistence.repository.fee.FeeMasterRepository;
import com.dw.ngms.cis.persistence.repository.system.SystemConfigurationRepository;
import com.dw.ngms.cis.persistence.repository.workflow.WorkflowDocumentRepository;
import com.dw.ngms.cis.service.dto.EmailDto;
import com.dw.ngms.cis.service.dto.cart.CartInvoiceItemDto;
import com.dw.ngms.cis.service.dto.workflow.InvoiceItemsDto;
import com.dw.ngms.cis.service.mapper.CartItemsMapper;
import com.dw.ngms.cis.service.workflow.PdfGeneratorService;
import com.dw.ngms.cis.utilities.CartUtils;
import com.dw.ngms.cis.utilities.TemplateUtils;
import com.dw.ngms.cis.web.request.InvoiceItemCostVm;
import com.dw.ngms.cis.web.vm.InvoiceTemplateVm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : prateekgoel
 * @since : 26/01/21, Tue
 **/
@Service
@Slf4j
@AllArgsConstructor
public class InvoiceService {

    private final CartRepository cartRepository;

    private final CartItemRepository cartItemRepository;

    private final PdfGeneratorService pdfGeneratorService;

    private final FeeMasterRepository feeMasterRepository;

    private final PaymentRepository paymentRepository;

    private final ObjectMapper mapper;

    private final WorkflowDocumentRepository workflowDocumentRepository;

    private final FileStorageService fileStorageService;

    private final CartItemsMapper cartItemsMapper;

    private final TemplateRepository templateRepository;

    private final ObjectMapper objectMapper;

    private final SystemConfigurationRepository systemConfigurationRepository;

    private final CartUtils cartUtils;

    private final TemplateUtils templateUtils;

    /**
     * @param invoiceItemsDto {@link InvoiceItemsDto}
     * @return Boolean
     */
    @Transactional
    public Boolean partialSaveInvoiceDetails(final InvoiceItemsDto invoiceItemsDto) {

        invoiceItemsDto.getCartItemsInvoiceDtos()
                .forEach(cartItemsDto -> cartItemRepository.save(cartItemsMapper.cartItemsDtoToCartItems(cartItemsDto)));

        return true;

    }

    @Transactional
    public Boolean saveInvoiceDetails(final InvoiceItemsDto invoiceItemsDto) throws JsonProcessingException {
        // insert all data based on workflowId in cart_items table
        invoiceItemsDto.getCartItemsInvoiceDtos()
                .forEach(cartItemsDto -> cartItemRepository.save(cartItemsMapper.cartItemsDtoToCartItems(cartItemsDto)));

        final Payment payment = addInvoiceToPayment(invoiceItemsDto);

        return payment != null;
    }


    public Payment addInvoiceToPayment(final InvoiceItemsDto invoiceItemsDto) throws JsonProcessingException {
        Payment payment = paymentRepository.findByWorkflowId(invoiceItemsDto.getWorkflowId());
        if (null != payment) {
            payment.setInvoiceAmount(invoiceItemsDto.getTotalInvoiceCost());
            final SystemConfiguration byTag = systemConfigurationRepository
                    .findByTag(SystemConfigurationType.INVOICE_PAYMENT_DUE_DAYS.getSystemConfigurationType());
            if (null != byTag) {
                final String tagValue = byTag.getTagValue();
                payment.setInvoiceDueDate(LocalDateTime.now().plusDays(Long.parseLong(tagValue)));
            }
            final EmailDto previewMail = getPreviewMail(invoiceItemsDto.getWorkflowId());
            payment.setInvoiceEmail(objectMapper.writeValueAsString(previewMail));
        } else {

            payment = new Payment();
            payment.setInvoiceAmount(invoiceItemsDto.getTotalInvoiceCost());
            final SystemConfiguration byTag = systemConfigurationRepository
                    .findByTag(SystemConfigurationType.INVOICE_PAYMENT_DUE_DAYS.getSystemConfigurationType());
            if (null != byTag) {
                final String tagValue = byTag.getTagValue();
                payment.setInvoiceDueDate(LocalDateTime.now().plusDays(Long.parseLong(tagValue)));
            }
            final EmailDto previewMail = getPreviewMail(invoiceItemsDto.getWorkflowId());
            payment.setInvoiceEmail(objectMapper.writeValueAsString(previewMail));
        }
        payment.setInvoiceGenerationDate(LocalDateTime.now());
        payment.setLastUpdatedOn(LocalDateTime.now());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentReferenceId("1234");
        payment.setInvoiceComment(invoiceItemsDto.getNotes());
        payment.setWorkflowId(invoiceItemsDto.getWorkflowId());
        payment.setUserId(invoiceItemsDto.getUserId());


        return paymentRepository.save(payment);

    }

    /**
     * @param workflowId workflowId
     * @return {@link FileSystemResource}
     */
    public Resource generateInvoicePdf(final Long workflowId) {

        String generatedFilePath = pdfGeneratorService.generatePdf(workflowId, false);

        return new FileSystemResource(generatedFilePath);
    }

    /**
     * @param invoiceItemCostVm {@link InvoiceItemCostVm}
     * @return InvoiceItemCostProjection
     */
    public InvoiceItemCostProjection getInvoiceItemCost(final InvoiceItemCostVm invoiceItemCostVm) {
        return feeMasterRepository.getItemCost(invoiceItemCostVm.getSearchDataTypeId(),
                invoiceItemCostVm.getFormatListItemId(),
                invoiceItemCostVm.getPaperSizeListItemId(),
                invoiceItemCostVm.getSubTypeListItemId(),
                invoiceItemCostVm.getDataTypeListItemId());
    }

    /**
     * @param workflowId workflowId
     * @return Collection<CartInvoiceItemDto>
     */
    public Collection<CartInvoiceItemDto> generateInvoiceData(final Long workflowId) {

        Cart byWorkflowId = cartRepository.findByWorkflowId(workflowId);
        Set<CartData> cartData = byWorkflowId.getCartData();

        Collection<CartItems> allByWorkflowId = cartItemRepository.findAllByWorkflowIdOrderBySno(workflowId);

        Map<Long, List<CartItems>> collect = allByWorkflowId
                .stream()
                .collect(Collectors.groupingBy(CartItems::getCartDataId));

        return collect.entrySet()
                .stream()
                .map(longListEntry -> {
                    CartInvoiceItemDto cartInvoiceItemDto = new CartInvoiceItemDto();
                    Long cartDataId = longListEntry.getKey();
                    Optional<CartData> first = cartData.stream()
                            .filter(cartData1 -> cartData1.getId().equals(cartDataId))
                            .findFirst();
                    if (first.isPresent()) {
                        String jsonData = first.get().getJsonData();
                        try {

                            CartJsonData cartJsonData = mapper.readerFor(CartJsonData.class)
                                    .readValue(jsonData);

                            cartInvoiceItemDto.setSearchDetails(cartJsonData.getSearchDetails());
                            cartInvoiceItemDto.setTemplateListItemId(cartJsonData.getTemplateListItemId());
                            cartInvoiceItemDto.setCartItems(longListEntry.getValue()
                                    .stream()
                                    .map(cartItemsMapper::cartItemsToCartItemsInvoiceDto)
                                    .collect(Collectors.toCollection(LinkedList::new)));

                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                            log.error(" error occurred while parsing cart json data with cause {}", e.getMessage());
                        }

                    } else if (cartDataId == -1) {

                        cartInvoiceItemDto.setCartItems(longListEntry.getValue()
                                .stream()
                                .map(cartItemsMapper::cartItemsToCartItemsInvoiceDto)
                                .collect(Collectors.toCollection(LinkedList::new)));

                    }
                    return cartInvoiceItemDto;
                }).collect(Collectors.toCollection(LinkedList::new));


    }

    /**
     * @param workflowId workflowId
     * @return {@link Resource}
     */
    public Resource getInvoice(final Long workflowId) {

        WorkflowDocuments workflowDocumentsByWorkflowIdAndDocumentTypeId = workflowDocumentRepository
                .findWorkflowDocumentsByWorkflowIdAndDocumentTypeId(workflowId, 135L);

        String path = StorageContext.WORKFLOW.getStorageContext()
                + File.separator
                + workflowDocumentsByWorkflowIdAndDocumentTypeId.getDocumentId()
                + "."
                + workflowDocumentsByWorkflowIdAndDocumentTypeId.getExtension();
        log.debug("loading invoice from location {}",path);
        Resource resource = fileStorageService.loadFileAsResource(path);
        if (null == resource)
            throw new ResourceNotFoundException(" Invoice not submitted yet");

        return resource;
    }

    public EmailDto getPreviewMail(final Long workflowId) {
        final Payment paymentByWorkflowId = paymentRepository.findByWorkflowId(workflowId);
        if (null != paymentByWorkflowId && StringUtils.isNotBlank(paymentByWorkflowId.getInvoiceEmail())) {
            final String invoiceEmail = paymentByWorkflowId.getInvoiceEmail();
            return templateUtils.getEmailFromEmailTemplate(invoiceEmail);
        }

        final Requester requesterDetails = cartUtils.getRequesterDetails(workflowId);

        final Map<String, String> placeHolderForInvoiceTemplate = getPlaceHolderForInvoiceTemplate(requesterDetails);

        final Template byTemplateId = templateRepository.findByTemplateId(15L);

        final String emailDetails = byTemplateId.getEmailDetails();

        final EmailDto mailFromEmailTemplate = templateUtils.getEmailFromEmailTemplate(emailDetails);

        final String mailBodyContent = MailUtils
                .substituteStringToken(mailFromEmailTemplate.getBody(), placeHolderForInvoiceTemplate);

        mailFromEmailTemplate.setBody(mailBodyContent);

        return mailFromEmailTemplate;

    }

    public EmailDto updateInvoiceEmailTemplate(final InvoiceTemplateVm invoiceTemplateVm) {
        Payment payment = paymentRepository.findByWorkflowId(invoiceTemplateVm.getWorkflowId());
        try {

            if (null != payment) {
                payment.setInvoiceEmail(objectMapper.writeValueAsString(invoiceTemplateVm.getEmailDto()));
                payment.setLastUpdatedOn(LocalDateTime.now());
                payment.setWorkflowId(invoiceTemplateVm.getWorkflowId());
                final Payment save = paymentRepository.saveAndFlush(payment);
                return objectMapper.readValue(save.getInvoiceEmail(), EmailDto.class);
            } else {
                final EmailDto emailDto = invoiceTemplateVm.getEmailDto();
                final Payment payment1 = new Payment();
                payment1.setLastUpdatedOn(LocalDateTime.now());
                payment1.setWorkflowId(invoiceTemplateVm.getWorkflowId());
                payment1.setInvoiceEmail(objectMapper.writeValueAsString(emailDto));
                final Payment save = paymentRepository.saveAndFlush(payment1);
                return objectMapper.readValue(save.getInvoiceEmail(), EmailDto.class);
            }

        } catch (Exception e) {
            log.error(" error occurred while updating email template {}", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    private Map<String, String> getPlaceHolderForInvoiceTemplate(final Requester requesterDetails) {
        if (null == requesterDetails)
            return null;
        Map<String, String> placeholder = new HashMap<>();
        final String firstName = requesterDetails.getFirstName();
        final String surName = requesterDetails.getSurName();
        placeholder.put("firstName", firstName);
        placeholder.put("surName", surName);
        return placeholder;
    }


}
