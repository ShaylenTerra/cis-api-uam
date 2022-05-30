package com.dw.ngms.cis.service.workflow;

import com.dw.ngms.cis.cisemailer.service.Mail;
import com.dw.ngms.cis.configuration.SearchProperties;
import com.dw.ngms.cis.connector.FtpService;
import com.dw.ngms.cis.enums.DeliveryMedium;
import com.dw.ngms.cis.enums.DeliveryMethod;
import com.dw.ngms.cis.enums.ProcessTemplateType;
import com.dw.ngms.cis.enums.SystemConfigurationType;
import com.dw.ngms.cis.persistence.domain.SgdataDocuments;
import com.dw.ngms.cis.persistence.domains.Template;
import com.dw.ngms.cis.persistence.domains.cart.Cart;
import com.dw.ngms.cis.persistence.domains.cart.Requester;
import com.dw.ngms.cis.persistence.domains.cart.RequesterInformation;
import com.dw.ngms.cis.persistence.domains.cart.SearchDetails;
import com.dw.ngms.cis.persistence.domains.system.SystemConfiguration;
import com.dw.ngms.cis.persistence.domains.workflow.Workflow;
import com.dw.ngms.cis.persistence.domains.workflow.WorkflowAction;
import com.dw.ngms.cis.persistence.repository.SgdataDocumentsRepository;
import com.dw.ngms.cis.persistence.repository.TemplateRepository;
import com.dw.ngms.cis.persistence.repository.cart.CartRepository;
import com.dw.ngms.cis.persistence.repository.system.SystemConfigurationRepository;
import com.dw.ngms.cis.persistence.repository.workflow.WorkflowActionRepository;
import com.dw.ngms.cis.persistence.repository.workflow.WorkflowRepository;
import com.dw.ngms.cis.service.EmailServiceHandler;
import com.dw.ngms.cis.service.dto.EmailDto;
import com.dw.ngms.cis.utilities.CartUtils;
import com.dw.ngms.cis.utilities.FileUtils;
import com.dw.ngms.cis.utilities.TemplateUtils;
import com.dw.ngms.cis.web.vm.workflow.ProcessNotificationsVm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : prateekgoel
 * @since : 23/06/21, Wed
 **/
@Component
@Slf4j
public class AutoDispatchNotification implements ProcessNotification {

    private final WorkflowActionRepository workflowActionRepository;

    private final WorkflowRepository workflowRepository;

    private final CartRepository cartRepository;

    private final CartUtils cartUtils;

    private final TemplateRepository templateRepository;

    private final TemplateUtils templateUtils;

    private final EmailServiceHandler emailServiceHandler;

    private final SystemConfigurationRepository systemConfigurationRepository;

    private final SgdataDocumentsRepository sgdataDocumentsRepository;

    private final FtpService ftpService;

    private final SearchProperties searchProperties;

    @Autowired
    public AutoDispatchNotification(WorkflowActionRepository workflowActionRepository,
                                    WorkflowRepository workflowRepository,
                                    CartRepository cartRepository,
                                    CartUtils cartUtils,
                                    TemplateRepository templateRepository,
                                    TemplateUtils templateUtils,
                                    EmailServiceHandler emailServiceHandler,
                                    SystemConfigurationRepository systemConfigurationRepository,
                                    SgdataDocumentsRepository sgdataDocumentsRepository,
                                    FtpService ftpService,
                                    SearchProperties searchProperties) {
        this.workflowActionRepository = workflowActionRepository;
        this.workflowRepository = workflowRepository;
        this.cartRepository = cartRepository;
        this.cartUtils = cartUtils;
        this.templateRepository = templateRepository;
        this.templateUtils = templateUtils;
        this.emailServiceHandler = emailServiceHandler;
        this.systemConfigurationRepository = systemConfigurationRepository;
        this.sgdataDocumentsRepository = sgdataDocumentsRepository;
        this.ftpService = ftpService;
        this.searchProperties = searchProperties;
    }

    @Override
    public void process(ProcessNotificationsVm processNotificationsVm) throws Exception {

        final SystemConfiguration systemConfiguration = systemConfigurationRepository
                .findByTag(SystemConfigurationType.EMAIL_ATTACHMENT.getSystemConfigurationType());

        AutoDispatcherResources autoDispatcherResources = new AutoDispatcherResources();

        final String urlPrefix = searchProperties.getImagePrefixPath().getUrlPrefix();

        List<SearchDetails> searchDetails = new ArrayList<>();

        workflowActionRepository.getAutoDispatchProcess(processNotificationsVm.getWorkflowId())
                .forEach(autoDispatchWorkflow -> {

            final Cart cart = cartRepository.findByWorkflowId(autoDispatchWorkflow.getWorkflowId().longValue());

            final Collection<SgdataDocuments> documentsByRecordId = getDocumentsByRecordId(cart, searchDetails);

            documentsByRecordId.forEach(sgdataDocuments -> {
                        try {

                            final Collection<Resource> resources = autoDispatcherResources.getResources();
                            final FileSystemResource fileSystemResource = FileUtils
                                    .writeInputStreamToFile(new URL(urlPrefix + sgdataDocuments.getUrl()).openStream(), sgdataDocuments.getUrl());
                            resources.add(fileSystemResource);
                            autoDispatcherResources.setResources(resources);

                            final Collection<Long> fileSize = autoDispatcherResources.getFileSize();
                            fileSize.add(sgdataDocuments.getFileSize());
                            autoDispatcherResources.setFileSize(fileSize);

                        } catch (IOException e) {
                            log.error(" error occurred while opening sgdata document url with cause");
                            e.printStackTrace();
                        }

                    });
        });

        final Collection<Long> fileSize = autoDispatcherResources.getFileSize();
        final long totalFileSize = fileSize.stream()
                .filter(Objects::nonNull)
                .mapToLong(Long::longValue)
                .sum();

        final Long configuredEmailSize = Long.valueOf(systemConfiguration.getTagValue());

        final RequesterInformation requesterInformation = cartUtils
                .getRequesterInformation(processNotificationsVm.getWorkflowId());

        if (null != requesterInformation) {

            if (DeliveryMethod.ELECTRONIC.equals(requesterInformation.getDeliveryMethod())) {
                if (DeliveryMedium.FTP.equals(requesterInformation.getDeliveryMedium()) ||
                        totalFileSize / 1024 > configuredEmailSize) {
                    // send data by ftp
                    sendDatToFtp(autoDispatcherResources.getResources(), processNotificationsVm.getReferenceNo());

                } else if (DeliveryMedium.EMAIL.equals(requesterInformation.getDeliveryMedium())) {
                    // send data on email
                    sendAutoDispatchEmail(autoDispatcherResources.getResources(),
                            requesterInformation,
                            searchDetails,
                            processNotificationsVm.getReferenceNo());
                }

            }
            // after ftp/email close task
            closeAutoDispatcherTask(processNotificationsVm.getWorkflowId());

        } else {
            log.debug(" No requester Information found for workflowId [{}]", processNotificationsVm.getWorkflowId());
        }
    }


    private void sendAutoDispatchEmail(Collection<Resource> resources,
                                       RequesterInformation requesterInformation,
                                       List<SearchDetails> searchDetails,
                                       String referenceNumber) {

        final Template byTemplateId = templateRepository.findByTemplateId(ProcessTemplateType.AUTO_DISPATCH.getTemplateType());

        if (null != byTemplateId) {
            final String emailDetails = byTemplateId.getEmailDetails();

            final EmailDto emailFromEmailTemplate = templateUtils.getEmailFromEmailTemplate(emailDetails);

            if (null != emailFromEmailTemplate) {
                final Requester requesterDetails = requesterInformation.getRequesterDetails();
                Map<String, String> placeHolder = new HashMap<>();
                placeHolder.put("firstName", requesterDetails.getFirstName());
                placeHolder.put("surName", requesterDetails.getSurName());
                placeHolder.put("referenceNumber", referenceNumber);
                placeHolder.put("deliveryMethod", requesterInformation.getDeliveryMethod().name());
                placeHolder.put("deliveryMedium", requesterInformation.getDeliveryMedium().name());

                final String collect = searchDetails.stream()
                        .map(searchDetails1 -> searchDetails1.getLpi() + "-" + searchDetails1.getSgNo() + "-" + searchDetails1.getProvince())
                        .collect(Collectors.joining(","));

                placeHolder.put("details", collect);

                final String mailBodyContent = emailServiceHandler.getMailBodyContent(emailFromEmailTemplate.getBody(), placeHolder);

                final Mail mail = Mail.builder()
                        .isHtml(Boolean.TRUE)
                        .isMultipart(Boolean.TRUE)
                        .to(requesterDetails.getEmail())
                        .resources(resources)
                        .subject(emailFromEmailTemplate.getSubject())
                        .body(mailBodyContent).build();

                emailServiceHandler.sendEmail(mail);

            } else {
                log.debug("no email body is configured for templateId [{}", ProcessTemplateType.AUTO_DISPATCH.getTemplateType());
            }
        } else {
            log.debug(" No template registered for templateId [{}]", ProcessTemplateType.AUTO_DISPATCH.getTemplateType());
        }

    }


    private Collection<SgdataDocuments> getDocumentsByRecordId(final Cart cart, List<SearchDetails> searchDetailList) {
        if (null != cart) {

            return cart.getCartData().stream().map(cartData -> {
                final SearchDetails searchDetails = cartUtils.getSearchDetailsFromCartData(cartData.getId());
                searchDetailList.add(searchDetails);
                return sgdataDocumentsRepository.findByRecordId(searchDetails.getRecordId());
            }).flatMap(Collection::stream).collect(Collectors.toList());

        }
        return null;
    }

    private void sendDatToFtp(Collection<Resource> resources, String referenceNumber) {
        try {

            ftpService.open();

            String dir = FileUtils.PATH_SEPARATOR + referenceNumber;

            ftpService.createDirectory(dir);

            resources.forEach(resource -> ftpService.putResourceToPath(resource, dir));

            ftpService.logout();

            ftpService.close();

        } catch (Exception e) {
            log.error(" error occurred while sending resources to ftp");
            e.printStackTrace();
        }
    }

    private void closeAutoDispatcherTask(final Long workflowId){
        final Workflow byWorkflowId = workflowRepository.findByWorkflowId(workflowId);
        if(null != byWorkflowId) {
            byWorkflowId.setStatusId(2L);
            byWorkflowId.setInternalStatusId(21L);
            byWorkflowId.setExternalStatusId(21L);
            byWorkflowId.setCompletedOn(new Date());
            workflowRepository.save(byWorkflowId);
        }

        workflowActionRepository.findByWorkflowId(workflowId).forEach(workflowAction -> {
            workflowAction.setActedOn(LocalDateTime.now());
            workflowAction.setActionTaken(221L);
            workflowActionRepository.save(workflowAction);
        });
    }

    @Data
    public class AutoDispatcherResources {
        Collection<Resource> resources = new HashSet<>();
        Collection<Long> fileSize = new HashSet<>();
    }

}
