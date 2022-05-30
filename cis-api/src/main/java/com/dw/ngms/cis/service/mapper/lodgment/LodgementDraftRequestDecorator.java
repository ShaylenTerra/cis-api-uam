package com.dw.ngms.cis.service.mapper.lodgment;

import com.dw.ngms.cis.persistence.domains.listmanagement.ListItem;
import com.dw.ngms.cis.persistence.domains.lodgement.LodgementDraftRequest;
import com.dw.ngms.cis.persistence.domains.lodgement.LodgementDraftStep;
import com.dw.ngms.cis.persistence.domains.reservation.ReservationOutcome;
import com.dw.ngms.cis.persistence.domains.workflow.Workflow;
import com.dw.ngms.cis.persistence.repository.listmanagement.ListItemRepository;
import com.dw.ngms.cis.persistence.repository.lodgement.LodgementDraftStepsRepository;
import com.dw.ngms.cis.persistence.repository.reservation.ReservationOutcomeRepository;
import com.dw.ngms.cis.persistence.repository.workflow.WorkflowRepository;
import com.dw.ngms.cis.service.dto.lodgment.LodgementDraftRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author prateek on 12-04-2022
 */
public abstract class LodgementDraftRequestDecorator implements LodgementDraftRequestMapper {

    @Autowired
    @Qualifier("delegate")
    private LodgementDraftRequestMapper delegate;

    @Autowired
    private LodgementDraftStepsRepository lodgementDraftStepsRepository;

    @Autowired
    private WorkflowRepository workflowRepository;

    @Autowired
    private ReservationOutcomeRepository reservationOutcomeRepository;

    @Autowired
    private ListItemRepository listItemRepository;

    @Override
    public LodgementDraftRequest
    lodgementDraftRequestDtoToLodgementDraftRequest(LodgementDraftRequestDto lodgementDraftRequestDto) {
        LodgementDraftRequest lodgementDraftRequest = delegate.lodgementDraftRequestDtoToLodgementDraftRequest(lodgementDraftRequestDto);
        if(null != lodgementDraftRequestDto) {

            Long stepId = lodgementDraftRequestDto.getStepId();
            LodgementDraftStep byStepId = lodgementDraftStepsRepository.findByStepId(stepId);
            if (null != byStepId) {
                lodgementDraftRequest.setLodgementDraftStep(byStepId);
            }
        }
        return lodgementDraftRequest;
    }

    @Override
    public LodgementDraftRequestDto
    lodgementDraftRequestToLodgementDraftRequestDto(LodgementDraftRequest lodgementDraftRequest) {
        LodgementDraftRequestDto lodgementDraftRequestDto = delegate.lodgementDraftRequestToLodgementDraftRequestDto(lodgementDraftRequest);

        if(null != lodgementDraftRequest) {
            LodgementDraftStep lodgementDraftStep = lodgementDraftRequest.getLodgementDraftStep();
            if (null != lodgementDraftStep) {
                Long stepId = lodgementDraftStep.getStepId();
                lodgementDraftRequestDto.setStepId(stepId);
            }

            Long reservationWorkflowId = lodgementDraftRequest.getReservationWorkflowId();

            Workflow byWorkflowId = workflowRepository
                    .findByWorkflowId(reservationWorkflowId);

            if(null != byWorkflowId) {
                lodgementDraftRequestDto.setReservationReferenceNumber(byWorkflowId.getReferenceNo());
            }

            Long outcomeIdReservation = lodgementDraftRequest.getOutcomeIdReservation();

            ReservationOutcome byOutcomeId = reservationOutcomeRepository.findByOutcomeId(outcomeIdReservation);

            if(null != byOutcomeId) {

                Long statusItemId = byOutcomeId.getStatusItemId();

                ListItem byItemId = listItemRepository.findByItemId(statusItemId);

                lodgementDraftRequestDto.setStatus(byItemId.getCaption());

            }

        }

        return lodgementDraftRequestDto;
    }
}
