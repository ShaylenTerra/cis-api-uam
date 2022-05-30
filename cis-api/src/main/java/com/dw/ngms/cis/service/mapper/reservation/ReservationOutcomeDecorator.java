package com.dw.ngms.cis.service.mapper.reservation;

import com.dw.ngms.cis.persistence.domains.reservation.ReservationDraftSteps;
import com.dw.ngms.cis.persistence.domains.reservation.ReservationOutcome;
import com.dw.ngms.cis.persistence.repository.reservation.ReservationDraftStepsRepository;
import com.dw.ngms.cis.service.dto.reservation.ReservationOutcomeDto;
import com.dw.ngms.cis.service.mapper.lodgment.LodgementDraftMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author prateek on 12-04-2022
 */
public abstract class ReservationOutcomeDecorator implements ReservationOutcomeMapper {

    @Autowired
    @Qualifier("delegate")
    private ReservationOutcomeMapper delegate;

    @Autowired
    private ReservationDraftStepsRepository reservationDraftStepsRepository;

    @Override
    public ReservationOutcome reservationOutcomeDtoToReservationOutcome(ReservationOutcomeDto reservationOutcomeDto) {
        ReservationOutcome reservationOutcome = delegate
                .reservationOutcomeDtoToReservationOutcome(reservationOutcomeDto);
        Long stepId = reservationOutcomeDto.getStepId();

        if (null != stepId) {

            ReservationDraftSteps byStepId = reservationDraftStepsRepository.findByStepId(stepId);

            reservationOutcome.setReservationDraftSteps(byStepId);

        }

        return reservationOutcome;
    }

    @Override
    public ReservationOutcomeDto reservationOutcomeToReservationOutcomeDto(ReservationOutcome reservationOutcome) {
        ReservationOutcomeDto reservationOutcomeDto = delegate
                .reservationOutcomeToReservationOutcomeDto(reservationOutcome);

        ReservationDraftSteps reservationDraftSteps = reservationOutcome.getReservationDraftSteps();

        if (null != reservationDraftSteps) {

            Long stepId = reservationDraftSteps.getStepId();

            reservationOutcomeDto.setStepId(stepId);

        }

        return reservationOutcomeDto;
    }
}
