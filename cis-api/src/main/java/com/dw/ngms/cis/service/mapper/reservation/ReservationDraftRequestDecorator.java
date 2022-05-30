package com.dw.ngms.cis.service.mapper.reservation;

import com.dw.ngms.cis.persistence.domains.reservation.ReservationDraftRequest;
import com.dw.ngms.cis.persistence.domains.reservation.ReservationDraftSteps;
import com.dw.ngms.cis.persistence.repository.reservation.ReservationDraftStepsRepository;
import com.dw.ngms.cis.service.dto.reservation.ReservationDraftRequestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @author prateek on 03-01-2022
 */
public abstract class ReservationDraftRequestDecorator implements ReservationDraftRequestMapper {

    @Autowired
    @Qualifier("delegate")
    private ReservationDraftRequestMapper delegate;

    @Autowired
    private ReservationDraftStepsRepository reservationDraftStepsRepository;

    @Override
    public ReservationDraftRequestDto reservationDraftRequestToReservationDraftRequestDto(ReservationDraftRequest reservationDraftRequest) {
        ReservationDraftRequestDto reservationDraftRequestDto = delegate
                .reservationDraftRequestToReservationDraftRequestDto(reservationDraftRequest);
        if (null != reservationDraftRequest) {
            ReservationDraftSteps reservationDraftSteps = reservationDraftRequest.getReservationDraftSteps();
            if (null != reservationDraftSteps) {
                reservationDraftRequestDto.setStepId(reservationDraftSteps.getStepId());
            }
        }
        return reservationDraftRequestDto;
    }

    @Override
    public ReservationDraftRequest reservationDraftRequestDtoToReservationDraftRequest(ReservationDraftRequestDto reservationDraftRequestDto) {
        ReservationDraftRequest reservationDraftRequest = delegate.reservationDraftRequestDtoToReservationDraftRequest(reservationDraftRequestDto);
        Long stepId = reservationDraftRequestDto.getStepId();
        if(null != stepId) {
            ReservationDraftSteps byStepId = reservationDraftStepsRepository.findByStepId(stepId);
            if(null != byStepId) {
                reservationDraftRequest.setReservationDraftSteps(byStepId);
            }
        }
        return reservationDraftRequest;
    }
}
