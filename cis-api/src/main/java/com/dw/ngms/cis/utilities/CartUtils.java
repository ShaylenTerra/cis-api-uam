package com.dw.ngms.cis.utilities;

import com.dw.ngms.cis.connector.FtpService;
import com.dw.ngms.cis.enums.StorageContext;
import com.dw.ngms.cis.persistence.domains.cart.*;
import com.dw.ngms.cis.persistence.domains.workflow.Workflow;
import com.dw.ngms.cis.persistence.domains.workflow.WorkflowDocuments;
import com.dw.ngms.cis.persistence.repository.cart.CartDataRepository;
import com.dw.ngms.cis.persistence.repository.cart.CartDispatchRepository;
import com.dw.ngms.cis.persistence.repository.cart.CartItemsDispatchDocsRepository;
import com.dw.ngms.cis.persistence.repository.cart.CartRepository;
import com.dw.ngms.cis.persistence.repository.workflow.WorkflowDocumentRepository;
import com.dw.ngms.cis.persistence.repository.workflow.WorkflowRepository;
import com.dw.ngms.cis.service.FileStorageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author : prateekgoel
 * @since : 30/05/21, Sun
 **/
@Component
@RequiredArgsConstructor
@Slf4j
public class CartUtils {

    private static final String TEMP_PATH = System.getProperty("java.io.tmpdir");

    private final CartRepository cartRepository;

    private final ObjectMapper objectMapper;

    private final CartItemsDispatchDocsRepository cartItemsDispatchDocsRepository;

    private final CartDispatchRepository cartDispatchRepository;

    private final FileStorageService fileStorageService;

    private final WorkflowDocumentRepository workflowDocumentRepository;

    private final WorkflowRepository workflowRepository;

    private final FtpService ftpService;

    private final CartDataRepository cartDataRepository;


    public RequesterInformation getRequesterInformation(final Long workflowId) {
        if (null == workflowId)
            return null;
        log.debug(" getting requester information for workflowId {}", workflowId);
        final Cart cartRepositoryByWorkflowId = cartRepository.findByWorkflowId(workflowId);
        if (null != cartRepositoryByWorkflowId) {
            try {
                return objectMapper
                        .readValue(cartRepositoryByWorkflowId.getRequesterInformation(), RequesterInformation.class);
            } catch (JsonProcessingException e) {
                log.error(" exception occurs while parsing Requester Information for workflowId {}", workflowId);
                e.printStackTrace();
            }
        }
        return null;
    }

    public Requester getRequesterDetails(final Long workflowId) {

        final RequesterInformation requesterInformation = getRequesterInformation(workflowId);
        final Requester requesterDetails = requesterInformation.getRequesterDetails();
        if (null != requesterDetails) {
            return requesterDetails;
        }

        return requesterInformation.getRequestLoggedBy();

    }

    public void zippedDispatchedDocument(final Long workflowId, ZipOutputStream zipOutputStream) {
        try {
            cartItemsDispatchDocsRepository
                    .findCartItemsDispatchDocsByWorkflowId(workflowId).forEach(cartItemsDispatchDocs -> {
                try {
                    Long documentId = cartItemsDispatchDocs.getDocumentId();
                    WorkflowDocuments workflowDocumentsByDocumentId = workflowDocumentRepository
                            .findWorkflowDocumentsByDocumentId(documentId);
                    final String location = StorageContext.WORKFLOW.getStorageContext() +
                            FileUtils.PATH_SEPARATOR +
                            workflowDocumentsByDocumentId.getDocumentId() +
                            FileUtils.EXTENSION_SEPARATOR +
                            workflowDocumentsByDocumentId.getExtension();
                    Resource resource = fileStorageService.loadFileAsResource(location);
                    ZipEntry zipEntry = new ZipEntry(workflowDocumentsByDocumentId.getDocumentId() + "-" + workflowDocumentsByDocumentId.getDocName());
                    zipOutputStream.putNextEntry(zipEntry);
                    StreamUtils.copy(resource.getInputStream(), zipOutputStream);
                    zipOutputStream.closeEntry();

                } catch (Exception e) {
                    log.error(" exception occur while zipping dispatch documents ");
                    e.printStackTrace();
                }

            });

            zipOutputStream.finish();

        } catch (IOException e) {
            log.error("exception occur while zipping dispatch documents ");
            e.printStackTrace();
        }
    }

    public CartDispatchAdditionalInfo cartDispatchAdditionalInfo(final Long workflowId) {
        final CartDispatch byWorkflowId = cartDispatchRepository.findByWorkflowId(workflowId);

        if (null != byWorkflowId) {
            final String dispatchDetails = byWorkflowId.getDispatchDetails();
            try {

                final CartDispatchAdditionalInfo cartDispatchAdditionalInfo = objectMapper.readerFor(CartDispatchAdditionalInfo.class).readValue(dispatchDetails);
                cartDispatchAdditionalInfo.setCartDispatchId(byWorkflowId.getId());
                return cartDispatchAdditionalInfo;

            } catch (JsonProcessingException e) {
                log.error("error occured while parsing cart dispatch data for workflowId [{}]", workflowId);
                e.printStackTrace();
            }
        }
        log.debug(" no cart dispatch records found against workflowId {}", workflowId);
        return null;
    }

    public String uploadDispatchDocsOnFtp(final Long workflowId) {

        Workflow byWorkflowId = workflowRepository.findByWorkflowId(workflowId);
        String storageLocation = File.separator + byWorkflowId.getReferenceNo() + File.separator;
        File tempFile = new File(TEMP_PATH + File.separator + "docs.zip");
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(tempFile))) {
            ftpService.open();
            ftpService.createDirectory(storageLocation);

            zippedDispatchedDocument(workflowId, zipOutputStream);

            zipOutputStream.close();

            ftpService.putFileToPath(tempFile, storageLocation);

            ftpService.logout();

            ftpService.close();

        } catch (Exception e) {
            log.error("exception occur while opening ftp connection ");
            e.printStackTrace();
        }

        return storageLocation;

    }

    public SearchDetails getSearchDetailsFromCartData(final Long cartDataId) {
        final Optional<CartData> byId = cartDataRepository.findById(cartDataId);
        if (byId.isPresent()) {
            final CartData cartData = byId.get();
            final String jsonData = cartData.getJsonData();
            try {

                final CartJsonData cartJsonData = objectMapper.readValue(jsonData, CartJsonData.class);
                return cartJsonData.getSearchDetails();


            } catch (JsonProcessingException e) {
                log.error(" error occurred while parsing cartJsonData for cartDataId [{}]",cartDataId);
                e.printStackTrace();
            }
        }
        return null;
    }
}
