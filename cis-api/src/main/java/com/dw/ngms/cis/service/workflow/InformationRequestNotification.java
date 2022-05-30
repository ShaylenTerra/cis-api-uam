package com.dw.ngms.cis.service.workflow;

import com.dw.ngms.cis.persistence.domains.cart.Requester;
import com.dw.ngms.cis.persistence.repository.TemplateRepository;
import com.dw.ngms.cis.service.EmailServiceHandler;
import com.dw.ngms.cis.utilities.CartUtils;
import com.dw.ngms.cis.utilities.TemplateUtils;
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
public class InformationRequestNotification implements ProcessNotification {

    private final EmailServiceHandler emailServiceHandler;

    private final CartUtils cartUtils;

    @Override
    public void process(ProcessNotificationsVm processNotificationsVm) throws Exception {
        log.debug("processing information request email ");
        final Requester requesterDetails = cartUtils.getRequesterDetails(processNotificationsVm.getWorkflowId());

        Map<String, String> placeHolder = new HashMap<>();
        placeHolder.put("firstName", requesterDetails.getFirstName());
        placeHolder.put("surName", requesterDetails.getSurName());
        placeHolder.put("referenceNumber", processNotificationsVm.getReferenceNo());

        emailServiceHandler.sendWorkflowMail(placeHolder,
                processNotificationsVm.getTemplateId(), requesterDetails.getEmail());

    }
}
