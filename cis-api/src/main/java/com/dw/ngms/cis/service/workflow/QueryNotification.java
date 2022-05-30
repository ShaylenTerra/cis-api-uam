package com.dw.ngms.cis.service.workflow;

import com.dw.ngms.cis.persistence.domains.User;
import com.dw.ngms.cis.persistence.domains.workflow.Workflow;
import com.dw.ngms.cis.persistence.repository.UserRepository;
import com.dw.ngms.cis.persistence.repository.workflow.WorkflowRepository;
import com.dw.ngms.cis.service.EmailServiceHandler;
import com.dw.ngms.cis.web.vm.workflow.ProcessNotificationsVm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : prateekgoel
 * @since : 31/05/21, Mon
 **/
@Component
@Slf4j
@AllArgsConstructor
public class QueryNotification implements ProcessNotification {

    private final UserRepository userRepository;

    private final WorkflowRepository workflowRepository;

    private final EmailServiceHandler emailServiceHandler;

    @Override
    public void process(final ProcessNotificationsVm processNotificationsVm) throws Exception {

        if (StringUtils.isNotBlank(processNotificationsVm.getEmail())) {
            log.debug("Getting query notification request from portal sending email to [{}]",
                    processNotificationsVm.getEmail());
            Map<String, String> placeholder = new HashMap<>();
            placeholder.put("firstName", processNotificationsVm.getFullName());
            placeholder.put("referenceNumber", processNotificationsVm.getReferenceNo());
            emailServiceHandler.sendWorkflowMail(placeholder,
                    processNotificationsVm.getTemplateId(),
                    processNotificationsVm.getEmail());

        } else {

            final User userByUserId = userRepository
                    .findUserByUserId(processNotificationsVm.getUserId());

            if (null != userByUserId) {

                Map<String, String> placeholder = new HashMap<>();
                placeholder.put("firstName", userByUserId.getFirstName());
                placeholder.put("surName", userByUserId.getSurname());
                placeholder.put("referenceNumber", processNotificationsVm.getReferenceNo());
                emailServiceHandler.sendWorkflowMail(placeholder,
                        processNotificationsVm.getTemplateId(),
                        userByUserId.getEmail());
            } else {
                log.debug(" user with id [{}] not found to send query notification  ", processNotificationsVm.getUserId());
            }
        }

    }
}
