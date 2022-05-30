package com.dw.ngms.cis.service.mapper.reservation;

import com.dw.ngms.cis.persistence.domains.listmanagement.ListItem;
import com.dw.ngms.cis.persistence.domains.reservation.ReservationDraft;
import com.dw.ngms.cis.persistence.domains.reservation.ReservationDraftSteps;
import com.dw.ngms.cis.persistence.repository.listmanagement.ListItemRepository;
import com.dw.ngms.cis.persistence.repository.reservation.ReservationDraftRepository;
import com.dw.ngms.cis.persistence.repository.reservation.ReservationDraftStepsRepository;
import com.dw.ngms.cis.service.dto.reservation.ReservationDraftStepsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author prateek on 06-01-2022
 */
public abstract class ReservationDraftStepsMapperDecorator implements ReservationDraftStepsMapper {

    @Autowired
    @Qualifier("delegate")
    private ReservationDraftStepsMapper delegate;

    @Autowired
    private ListItemRepository listItemRepository;

    @Autowired
    private ReservationDraftRepository reservationDraftRepository;

    @Override
    public ReservationDraftStepsDto
    reservationDraftStepsToReservationDraftStepsDto(ReservationDraftSteps reservationDraftSteps) {
        ReservationDraftStepsDto reservationDraftStepsDto = delegate
                .reservationDraftStepsToReservationDraftStepsDto(reservationDraftSteps);

        if (null != reservationDraftSteps) {

            Long reasonItemId = reservationDraftSteps.getReasonItemId();
            ListItem byItemId = listItemRepository.findByItemId(reasonItemId);
            if (null != byItemId) {
                reservationDraftStepsDto.setRequestReason(byItemId.getCaption());
            }
            ReservationDraft reservationDraft = reservationDraftSteps.getReservationDraft();
            if (null != reservationDraft) {
                reservationDraftStepsDto.setDraftId(reservationDraft.getDraftId());
            }
        }

        return reservationDraftStepsDto;
    }

    @Override
    public ReservationDraftSteps reservationDraftStepsDtoToReservationDraftSteps(ReservationDraftStepsDto reservationDraftStepsDto) {
        ReservationDraftSteps reservationDraftSteps = delegate
                .reservationDraftStepsDtoToReservationDraftSteps(reservationDraftStepsDto);
        if (null != reservationDraftSteps) {
            Long draftId = reservationDraftStepsDto.getDraftId();
            ReservationDraft byDraftId = reservationDraftRepository.findByDraftId(draftId);
            reservationDraftSteps.setReservationDraft(byDraftId);
        }
        return reservationDraftSteps;
    }

    @Override
    public ReservationDraftSteps reservationDraftStepsToReservationDraftSteps(ReservationDraftSteps reservationDraftSteps) {
        ReservationDraftSteps reservationDraftSteps1 = delegate.reservationDraftStepsToReservationDraftSteps(reservationDraftSteps);
        Long reasonItemId = reservationDraftSteps.getReasonItemId();
        ListItem byItemId = listItemRepository.findByItemId(reasonItemId);
        if(null != byItemId) {
            reservationDraftSteps1.setReasonName(byItemId.getCaption());
        }

        return reservationDraftSteps1;
    }
}
