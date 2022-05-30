package com.dw.ngms.cis.service.workflow;

import com.dw.ngms.cis.cisemailer.service.EmailService;
import com.dw.ngms.cis.cisemailer.service.Mail;
import com.dw.ngms.cis.enums.DocumentType;
import com.dw.ngms.cis.enums.StorageContext;
import com.dw.ngms.cis.enums.SystemConfigurationType;
import com.dw.ngms.cis.persistence.domains.Template;
import com.dw.ngms.cis.persistence.domains.cart.Requester;
import com.dw.ngms.cis.persistence.domains.system.SystemConfiguration;
import com.dw.ngms.cis.persistence.domains.workflow.WorkflowDocuments;
import com.dw.ngms.cis.persistence.repository.TemplateRepository;
import com.dw.ngms.cis.persistence.repository.system.SystemConfigurationRepository;
import com.dw.ngms.cis.persistence.repository.workflow.WorkflowDocumentRepository;
import com.dw.ngms.cis.service.EmailServiceHandler;
import com.dw.ngms.cis.service.FileStorageService;
import com.dw.ngms.cis.service.dto.EmailDto;
import com.dw.ngms.cis.utilities.CartUtils;
import com.dw.ngms.cis.utilities.FileUtils;
import com.dw.ngms.cis.utilities.TemplateUtils;
import com.dw.ngms.cis.web.vm.workflow.ProcessNotificationsVm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author : prateekgoel
 * @since : 31/05/21, Mon
 **/
@Component
@AllArgsConstructor
@Slf4j
public class PaymentVerificationNotification implements ProcessNotification {

    private final WorkflowDocumentRepository workflowDocumentRepository;

    private final FileStorageService fileStorageService;

    private final EmailServiceHandler emailServiceHandler;

    private final TemplateUtils templateUtils;

    private final TemplateRepository templateRepository;

    private final SystemConfigurationRepository systemConfigurationRepository;


    @Override
    public void process(final ProcessNotificationsVm processNotificationsVm) {

        final List<WorkflowDocuments> workflowDocuments = workflowDocumentRepository
                .findWorkflowDocumentsByWorkflowIdAndDocumentTypeIdIn(processNotificationsVm.getWorkflowId(),
                        Arrays.asList(DocumentType.INVOICE.getDocumentType(), DocumentType.POP.getDocumentType()));


        final List<Resource> resources = workflowDocuments.stream()
                .map(workflowDocuments1 -> {
                    String location = StorageContext.WORKFLOW.getStorageContext() +
                            FileUtils.PATH_SEPARATOR +
                            workflowDocuments1.getDocumentId() +
                            FileUtils.EXTENSION_SEPARATOR +
                            workflowDocuments1.getExtension();
                    log.debug(" loading resource from path {}", location);
                    return fileStorageService.loadFileAsResource(location);
                })
                .collect(Collectors.toList());

        final Template byTemplateId = templateRepository.findByTemplateId(processNotificationsVm.getTemplateId());

        final String emailDetails = byTemplateId.getEmailDetails();

        final EmailDto emailFromEmailTemplate = templateUtils.getEmailFromEmailTemplate(emailDetails);

        final SystemConfiguration byTag = systemConfigurationRepository
                .findByTag(SystemConfigurationType.SYSTEM_CASHIER.getSystemConfigurationType());

        Map<String, String> placeHolder = new HashMap<>();
        placeHolder.put("referenceNumber", processNotificationsVm.getReferenceNo());

        final String mailBodyContent = emailServiceHandler.getMailBodyContent(emailFromEmailTemplate.getBody(), placeHolder);

        final Mail mail = Mail.builder()
                .isHtml(Boolean.TRUE)
                .isMultipart(Boolean.TRUE)
                .to(byTag.getTagValue())
                .resources(resources)
                .subject(emailFromEmailTemplate.getSubject())
                .body(mailBodyContent).build();

        emailServiceHandler.sendEmail(mail);

    }
}
