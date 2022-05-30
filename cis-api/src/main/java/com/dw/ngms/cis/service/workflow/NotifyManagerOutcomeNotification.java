package com.dw.ngms.cis.service.workflow;

import com.dw.ngms.cis.cisemailer.service.Mail;
import com.dw.ngms.cis.persistence.domains.Template;
import com.dw.ngms.cis.persistence.domains.cart.Requester;
import com.dw.ngms.cis.persistence.domains.cart.RequesterInformation;
import com.dw.ngms.cis.persistence.domains.workflow.WorkflowProcessData;
import com.dw.ngms.cis.persistence.repository.TemplateRepository;
import com.dw.ngms.cis.service.EmailServiceHandler;
import com.dw.ngms.cis.service.dto.EmailDto;
import com.dw.ngms.cis.utilities.TemplateUtils;
import com.dw.ngms.cis.utilities.WorkflowUtils;
import com.dw.ngms.cis.web.vm.workflow.ProcessNotificationsVm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : prateekgoel
 * @since : 20/06/21, Sun
 **/
@Component
@AllArgsConstructor
@Slf4j
public class NotifyManagerOutcomeNotification implements ProcessNotification {

    private final WorkflowUtils workflowUtils;

    private final EmailServiceHandler emailServiceHandler;

    private final TemplateRepository templateRepository;

    private final TemplateUtils templateUtils;

    @Override
    public void process(ProcessNotificationsVm processNotificationsVm) throws Exception {

        final WorkflowProcessData processData = workflowUtils.getProcessData(processNotificationsVm.getWorkflowId());

        if (null != processData) {

            final RequesterInformation requesterInformation = processData.getRequesterInformation();

            final Requester requestLoggedBy = requesterInformation.getRequestLoggedBy();

            if (null != requestLoggedBy) {

                Map<String, String> placeHolder = new HashMap<>();
                placeHolder.put("firstName", requestLoggedBy.getFirstName());
                placeHolder.put("surName", requestLoggedBy.getSurName());
                placeHolder.put("referenceNumber", workflowUtils.getReferenceNumber(processNotificationsVm.getWorkflowId()));
                placeHolder.put("notes", workflowUtils.getDecisionNotesForWorkflowId(processNotificationsVm.getWorkflowId()));

                final Template byTemplateId = templateRepository.findByTemplateId(processNotificationsVm.getTemplateId());

                if (null != byTemplateId) {

                    final String emailDetails = byTemplateId.getEmailDetails();

                    final EmailDto emailFromEmailTemplate = templateUtils.getEmailFromEmailTemplate(emailDetails);

                    if (null != emailFromEmailTemplate) {

                        final String mailBodyContent = emailServiceHandler.getMailBodyContent(emailFromEmailTemplate.getBody(), placeHolder);

                        final Mail mail = Mail.builder()
                                .isHtml(Boolean.TRUE)
                                .isMultipart(Boolean.FALSE)
                                .to(requestLoggedBy.getEmail())
                                .resources(null)
                                .subject(emailFromEmailTemplate.getSubject())
                                .body(mailBodyContent).build();

                        emailServiceHandler.sendEmail(mail);
                    } else {
                        log.debug(" No Email Body configured for email templateId [{}]", byTemplateId.getTemplateId());
                    }

                } else {
                    log.debug("No Template found for templateId [{}]", processNotificationsVm.getTemplateId());
                }


            } else {
                log.debug("No requestLoggedBy Information available");
            }
        } else {
            log.debug(" No processData available for workflowId [{}]", processNotificationsVm.getWorkflowId());
        }
    }
}
