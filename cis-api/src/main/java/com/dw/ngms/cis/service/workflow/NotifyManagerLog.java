package com.dw.ngms.cis.service.workflow;

import com.dw.ngms.cis.persistence.domains.cart.Requester;
import com.dw.ngms.cis.persistence.domains.cart.RequesterInformation;
import com.dw.ngms.cis.persistence.domains.workflow.WorkflowProcessData;
import com.dw.ngms.cis.service.EmailServiceHandler;
import com.dw.ngms.cis.utilities.WorkflowUtils;
import com.dw.ngms.cis.web.vm.workflow.ProcessNotificationsVm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class NotifyManagerLog implements ProcessNotification {

    private final EmailServiceHandler emailServiceHandler;

    private final WorkflowUtils workflowUtils;

    @Override
    public void process(ProcessNotificationsVm processNotificationsVm) throws Exception {
        final WorkflowProcessData processData = workflowUtils.getProcessData(processNotificationsVm.getWorkflowId());
        if (null != processData) {
            final RequesterInformation requesterInformation = processData.getRequesterInformation();
            if (null != requesterInformation) {
                final Requester requesterDetails = requesterInformation.getRequesterDetails();

                if (null != requesterDetails) {
                    Map<String, String> placeholder = new HashMap<>();
                    placeholder.put("firstName", requesterDetails.getFirstName());
                    placeholder.put("surName", requesterDetails.getSurName());
                    placeholder.put("referenceNumber", processNotificationsVm.getReferenceNo());
                    emailServiceHandler.sendWorkflowMail(placeholder,
                            processNotificationsVm.getTemplateId(),
                            requesterDetails.getEmail());
                } else {
                    log.debug(" requester data not available for workflowId [{}] ", processNotificationsVm.getWorkflowId());
                }
            } else {
                log.debug("Requester Information not available for workflowId [{}] ", processNotificationsVm.getWorkflowId());
            }
        } else {
            log.debug(" process data not available for workflowId [{}]", processNotificationsVm.getWorkflowId());
        }
    }
}
