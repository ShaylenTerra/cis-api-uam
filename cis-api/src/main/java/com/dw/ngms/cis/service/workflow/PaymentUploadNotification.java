package com.dw.ngms.cis.service.workflow;

import com.dw.ngms.cis.cisemailer.service.EmailService;
import com.dw.ngms.cis.cisemailer.service.Mail;
import com.dw.ngms.cis.enums.DocumentType;
import com.dw.ngms.cis.enums.StorageContext;
import com.dw.ngms.cis.persistence.domains.Template;
import com.dw.ngms.cis.persistence.domains.cart.Requester;
import com.dw.ngms.cis.persistence.domains.workflow.WorkflowDocuments;
import com.dw.ngms.cis.persistence.repository.TemplateRepository;
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
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : prateekgoel
 * @since : 31/05/21, Mon
 **/
@Component
@AllArgsConstructor
@Slf4j
public class PaymentUploadNotification implements ProcessNotification {

    private final WorkflowDocumentRepository workflowDocumentRepository;

    private final CartUtils cartUtils;

    private final EmailServiceHandler emailServiceHandler;

    private final TemplateUtils templateUtils;

    private final TemplateRepository templateRepository;

    private final FileStorageService fileStorageService;

    @Override
    public void process(final ProcessNotificationsVm processNotificationsVm) {

        final Requester requesterDetails = cartUtils.getRequesterDetails(processNotificationsVm.getWorkflowId());

        final WorkflowDocuments workflowDocuments = workflowDocumentRepository
                .findWorkflowDocumentsByWorkflowIdAndDocumentTypeId(processNotificationsVm.getWorkflowId(),
                        DocumentType.POP.getDocumentType());

        String location = StorageContext.WORKFLOW.getStorageContext() +
                FileUtils.PATH_SEPARATOR +
                workflowDocuments.getDocumentId() +
                FileUtils.EXTENSION_SEPARATOR +
                workflowDocuments.getExtension();
        log.debug("loading payment upload resource from location {}", location);
        final Resource resource = fileStorageService.loadFileAsResource(location);

        final Template byTemplateId = templateRepository.findByTemplateId(processNotificationsVm.getTemplateId());

        final String emailDetails = byTemplateId.getEmailDetails();

        final EmailDto emailFromEmailTemplate = templateUtils.getEmailFromEmailTemplate(emailDetails);

        Map<String,String> placeHolder = new HashMap<>();
        placeHolder.put("firstName", requesterDetails.getFirstName());
        placeHolder.put("surName", requesterDetails.getSurName());
        placeHolder.put("referenceNumber", processNotificationsVm.getReferenceNo());

        final String mailBodyContent = emailServiceHandler.getMailBodyContent(emailFromEmailTemplate.getBody(), placeHolder);

        final Mail mail = Mail.builder()
                .isHtml(Boolean.TRUE)
                .isMultipart(Boolean.TRUE)
                .to(requesterDetails.getEmail())
                .resources(Collections.singletonList(resource))
                .subject(emailFromEmailTemplate.getSubject())
                .body(mailBodyContent).build();

        emailServiceHandler.sendEmail(mail);

    }
}
