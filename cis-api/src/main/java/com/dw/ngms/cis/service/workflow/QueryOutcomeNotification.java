package com.dw.ngms.cis.service.workflow;

import com.dw.ngms.cis.cisemailer.service.Mail;
import com.dw.ngms.cis.persistence.domains.Template;
import com.dw.ngms.cis.persistence.domains.cart.Requester;
import com.dw.ngms.cis.persistence.domains.cart.RequesterInformation;
import com.dw.ngms.cis.persistence.domains.workflow.QueryData;
import com.dw.ngms.cis.persistence.domains.workflow.WorkflowProcessData;
import com.dw.ngms.cis.persistence.repository.TemplateRepository;
import com.dw.ngms.cis.persistence.repository.UserRepository;
import com.dw.ngms.cis.persistence.repository.workflow.WorkflowActionRepository;
import com.dw.ngms.cis.persistence.repository.workflow.WorkflowDocumentRepository;
import com.dw.ngms.cis.persistence.repository.workflow.WorkflowRepository;
import com.dw.ngms.cis.service.EmailServiceHandler;
import com.dw.ngms.cis.service.dto.EmailDto;
import com.dw.ngms.cis.utilities.TemplateUtils;
import com.dw.ngms.cis.utilities.WorkflowUtils;
import com.dw.ngms.cis.web.vm.workflow.ProcessNotificationsVm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : prateekgoel
 * @since : 20/06/21, Sun
 **/
@Component
@AllArgsConstructor
@Slf4j
public class QueryOutcomeNotification implements ProcessNotification {


    private final EmailServiceHandler emailServiceHandler;

    private final WorkflowUtils workflowUtils;

    private final TemplateRepository templateRepository;

    private final TemplateUtils templateUtils;

    @Override
    public void process(ProcessNotificationsVm processNotificationsVm) throws Exception {
        final WorkflowProcessData processData = workflowUtils
                .getProcessData(processNotificationsVm.getWorkflowId());
        if (null != processData) {
            final RequesterInformation requesterInformation = processData.getRequesterInformation();
            if (null != requesterInformation) {
                final Requester requestLoggedBy = requesterInformation.getRequestLoggedBy();

                processEmail(processNotificationsVm.getWorkflowId(),
                        requestLoggedBy.getFirstName(),
                        requestLoggedBy.getSurName(),
                        requestLoggedBy.getEmail(),
                        processNotificationsVm.getTemplateId());
            } else {
                final QueryData queryData = processData.getQueryData();
                processEmail(processNotificationsVm.getWorkflowId(),
                        queryData.getFirstName(),
                        queryData.getSurName(),
                        queryData.getEmail(), processNotificationsVm.getTemplateId());
            }
        } else {
            log.debug(" no process data for workflowId [{}]", processNotificationsVm.getWorkflowId());
        }

    }

    public void processEmail(final Long workflowId,
                             final String firstName,
                             final String surName,
                             final String email,
                             final Long templateId) {

        log.debug("processing query outcome email for emailId [{}], firstName [{}],  surName [{}], workflowId [{}], templateId [{}]",
                email, firstName, surName, workflowId, templateId);

        Map<String, String> placeHolder = new HashMap<>();
        placeHolder.put("firstName", firstName);
        placeHolder.put("surName", surName);
        placeHolder.put("referenceNumber", workflowUtils
                .getReferenceNumber(workflowId));
        placeHolder.put("notes", workflowUtils
                .getDecisionNotesForWorkflowId(workflowId));


        final Template byTemplateId = templateRepository.findByTemplateId(templateId);

        final String emailDetails = byTemplateId.getEmailDetails();

        final EmailDto emailFromEmailTemplate = templateUtils.getEmailFromEmailTemplate(emailDetails);

        final String mailBodyContent = emailServiceHandler.getMailBodyContent(emailFromEmailTemplate.getBody(), placeHolder);

        final Mail mail = Mail.builder()
                .isHtml(Boolean.TRUE)
                .isMultipart(Boolean.FALSE)
                .to(email)
                .resources(null)
                .subject(emailFromEmailTemplate.getSubject())
                .body(mailBodyContent).build();

        emailServiceHandler.sendEmail(mail);

    }

}
