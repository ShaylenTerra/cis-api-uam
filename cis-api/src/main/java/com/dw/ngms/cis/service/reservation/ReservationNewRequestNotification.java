package com.dw.ngms.cis.service.reservation;

import com.dw.ngms.cis.persistence.domains.User;
import com.dw.ngms.cis.persistence.domains.reservation.ReservationDraft;
import com.dw.ngms.cis.persistence.domains.workflow.Workflow;
import com.dw.ngms.cis.persistence.domains.workflow.WorkflowAction;
import com.dw.ngms.cis.persistence.repository.UserRepository;
import com.dw.ngms.cis.persistence.repository.reservation.ReservationDraftRepository;
import com.dw.ngms.cis.persistence.repository.workflow.WorkflowActionRepository;
import com.dw.ngms.cis.persistence.repository.workflow.WorkflowRepository;
import com.dw.ngms.cis.service.EmailServiceHandler;
import com.dw.ngms.cis.service.workflow.ProcessNotification;
import com.dw.ngms.cis.web.vm.workflow.ProcessNotificationsVm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author prateek on 09-02-2022
 */
@Component
@Slf4j
@AllArgsConstructor
public class ReservationNewRequestNotification implements ProcessNotification {

    private final EmailServiceHandler emailServiceHandler;

    private final ReservationDraftRepository reservationDraftRepository;

    private final UserRepository userRepository;

    private final WorkflowRepository workflowRepository;

    @Override
    public void process(ProcessNotificationsVm processNotificationsVm) throws Exception {
        ReservationDraft byWorkflowId = reservationDraftRepository
                .findByWorkflowId(processNotificationsVm.getWorkflowId());

        Map<String, String> placeHolders = new HashMap<>();

        Long applicantUserId = byWorkflowId.getApplicantUserId();

        User userByUserId = userRepository.findUserByUserId(applicantUserId);

        String applicantEmail = "";

        // fetch email from user
        if (null != userByUserId) {
            placeHolders.put("firstName", userByUserId.getFirstName());
            placeHolders.put("surName", userByUserId.getSurname());
            applicantEmail = userByUserId.getEmail();
        }

        Workflow byWorkflowId1 = workflowRepository
                .findByWorkflowId(processNotificationsVm.getWorkflowId());

        // fetch reference number from workflow
        if(null != byWorkflowId1) {
            placeHolders.put("referenceNumber", byWorkflowId1.getReferenceNo());
        }

        emailServiceHandler.sendReservationMailNotification(placeHolders,
                processNotificationsVm.getTemplateId(),
                applicantEmail);

    }
}
