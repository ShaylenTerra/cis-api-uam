package com.dw.ngms.cis.service.mapper.reservation;

import com.dw.ngms.cis.persistence.domains.reservation.ReservationDraftRequestOutcome;
import com.dw.ngms.cis.persistence.domains.reservation.ReservationDraftSteps;
import com.dw.ngms.cis.persistence.repository.reservation.ReservationDraftRepository;
import com.dw.ngms.cis.persistence.repository.reservation.ReservationDraftStepsRepository;
import com.dw.ngms.cis.service.dto.reservation.ReservationDraftRequestOutcomeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author prateek on 03-01-2022
 */
public abstract class ReservationDraftRequestOutcomeDecorator implements ReservationDraftRequestOutcomeMapper {

    @Autowired
    @Qualifier("delegate")
    private ReservationDraftRequestOutcomeMapper delegate;

    @Autowired
    private ReservationDraftStepsRepository reservationDraftStepsRepository;


    @Override
    public ReservationDraftRequestOutcome
    reservationDraftRequestOutcomeDtoToReservationDraftRequestOutcome(
            ReservationDraftRequestOutcomeDto reservationDraftRequestOutcomeDto) {
        ReservationDraftRequestOutcome reservationDraftRequestOutcome = delegate
                .reservationDraftRequestOutcomeDtoToReservationDraftRequestOutcome(reservationDraftRequestOutcomeDto);
        Long stepId = reservationDraftRequestOutcomeDto.getStepId();
        if(null != stepId) {
            ReservationDraftSteps byStepId = reservationDraftStepsRepository.findByStepId(stepId);
            reservationDraftRequestOutcome.setReservationDraftSteps(byStepId);
        }

        return reservationDraftRequestOutcome;
    }

    @Override
    public ReservationDraftRequestOutcomeDto
    reservationDraftRequestOutcomeToReservationDraftRequestOutcomeDto(ReservationDraftRequestOutcome reservationDraftRequestOutcome) {
        ReservationDraftRequestOutcomeDto reservationDraftRequestOutcomeDto = delegate
                .reservationDraftRequestOutcomeToReservationDraftRequestOutcomeDto(reservationDraftRequestOutcome);
        if (null != reservationDraftRequestOutcomeDto) {
            ReservationDraftSteps reservationDraftSteps = reservationDraftRequestOutcome.getReservationDraftSteps();
            Long stepId = reservationDraftSteps.getStepId();
            reservationDraftRequestOutcomeDto.setStepId(stepId);
        }
        return reservationDraftRequestOutcomeDto;
    }


}
