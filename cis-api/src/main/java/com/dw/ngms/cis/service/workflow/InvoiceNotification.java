package com.dw.ngms.cis.service.workflow;

import com.dw.ngms.cis.cisemailer.service.EmailService;
import com.dw.ngms.cis.cisemailer.service.Mail;
import com.dw.ngms.cis.configuration.AppPropertiesConfig;
import com.dw.ngms.cis.enums.DocumentType;
import com.dw.ngms.cis.enums.StorageContext;
import com.dw.ngms.cis.persistence.domains.Payment;
import com.dw.ngms.cis.persistence.domains.cart.Requester;
import com.dw.ngms.cis.persistence.domains.workflow.WorkflowDocuments;
import com.dw.ngms.cis.persistence.repository.PaymentRepository;
import com.dw.ngms.cis.persistence.repository.cart.CartRepository;
import com.dw.ngms.cis.persistence.repository.workflow.WorkflowDocumentRepository;
import com.dw.ngms.cis.service.EmailServiceHandler;
import com.dw.ngms.cis.service.FileStorageService;
import com.dw.ngms.cis.service.dto.EmailDto;
import com.dw.ngms.cis.utilities.CartUtils;
import com.dw.ngms.cis.utilities.TemplateUtils;
import com.dw.ngms.cis.web.vm.workflow.ProcessNotificationsVm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.Date;

/**
 * @author : prateekgoel
 * @since : 31/05/21, Mon
 **/
@Component
@Slf4j
@AllArgsConstructor
public class InvoiceNotification implements ProcessNotification {


    private final PaymentRepository paymentRepository;

    private final CartUtils cartUtils;

    private final TemplateUtils templateUtils;

    private final WorkflowDocumentRepository workflowDocumentRepository;

    private final EmailServiceHandler emailServiceHandler;

    private final PdfGeneratorService pdfGeneratorService;

    private final AppPropertiesConfig appPropertiesConfig;

    @Override
    public void process(final ProcessNotificationsVm processNotificationsVm) throws Exception {
        final Requester requesterDetails = cartUtils
                .getRequesterDetails(processNotificationsVm.getWorkflowId());

        final Payment paymentRepositoryByWorkflowId = paymentRepository
                .findByWorkflowId(processNotificationsVm.getWorkflowId());

        final EmailDto emailDto = templateUtils
                .getEmailFromEmailTemplate(paymentRepositoryByWorkflowId.getInvoiceEmail());

        final String mailBodyContent = emailServiceHandler.getMailBodyContent(emailDto.getBody(), Collections.emptyMap());

        emailDto.setBody(mailBodyContent);

        // generate pdf
        final Resource resource = generatePdf(processNotificationsVm.getWorkflowId());
        // save to workflow Document
        final WorkflowDocuments workflowDocuments = savePdfToWorkflowDocument(resource,
                processNotificationsVm.getWorkflowId(),
                processNotificationsVm.getUserId());

        final Resource resource1 = moveInvoiceToWorkflow(workflowDocuments, resource);

        // attach pdf to mail
        // fetch invoice resource using workflowId

        final Mail mail = Mail.builder()
                .to(requesterDetails.getEmail())
                .subject(emailDto.getSubject())
                .body(emailDto.getBody())
                .resources(Collections.singletonList(resource1))
                .isMultipart(Boolean.TRUE)
                .isHtml(Boolean.TRUE).build();

        emailServiceHandler.sendEmail(mail);
    }


    private Resource generatePdf(final Long workflowId) {
        String generatedFilePath = pdfGeneratorService.generatePdf(workflowId, true);

        return new FileSystemResource(generatedFilePath);
    }

    private WorkflowDocuments savePdfToWorkflowDocument(final Resource resource, final Long workflowId, final Long userId) {
        WorkflowDocuments workflowDocuments = new WorkflowDocuments();
        workflowDocuments.setDocName(resource.getFilename());
        workflowDocuments.setUserId(userId);
        workflowDocuments.setDocumentTypeId(DocumentType.INVOICE.getDocumentType());
        workflowDocuments.setExtension("pdf");
        workflowDocuments.setUploadedOn(new Date());
        try {

            workflowDocuments.setSizeKb(resource.contentLength() / 1024);

        } catch (IOException e) {
            e.printStackTrace();
        }
        workflowDocuments.setUploadRefId(workflowId);
        workflowDocuments.setWorkflowId(workflowId);
        workflowDocuments.setNotes("Invoice generated");
        return workflowDocumentRepository.save(workflowDocuments);
    }

    private Resource moveInvoiceToWorkflow(final WorkflowDocuments workflowDocuments, final Resource resource) {
        final String storageLocation = appPropertiesConfig.getFileStorage().getStorageLocation();

        final Path targetPath = Paths.get(storageLocation +
                File.separator + StorageContext.WORKFLOW.getStorageContext() +
                File.separator + workflowDocuments.getDocumentId() +
                "." +
                workflowDocuments.getExtension());
        //create file at target path
        final Path file;
        try {

            file = Files.createFile(targetPath);
            Files.copy(resource.getInputStream(), file,
                    StandardCopyOption.REPLACE_EXISTING);

            return new FileSystemResource(file);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
