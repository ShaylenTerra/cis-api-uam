package com.dw.ngms.cis.service.reservation;

import com.dw.ngms.cis.persistence.domains.reservation.ReservationDraftSteps;
import com.dw.ngms.cis.persistence.repository.reservation.ReservationDraftStepsRepository;
import com.dw.ngms.cis.service.dto.reservation.ReservationDraftStepsDto;
import com.dw.ngms.cis.service.mapper.reservation.ReservationDraftStepsMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ReservationDraftStepsService {
    private final ReservationDraftStepsRepository reservationDraftStepsRepository;
    private final ReservationDraftStepsMapper reservationDraftStepsMapper;

    public ReservationDraftStepsDto saveReservationDraftSteps
            (final ReservationDraftStepsDto reservationDraftStepsDto) {
        ReservationDraftSteps reservationDraftSteps = reservationDraftStepsMapper
                .reservationDraftStepsDtoToReservationDraftSteps(reservationDraftStepsDto);

        ReservationDraftSteps saveReservationDraftSteps = reservationDraftStepsRepository.save(reservationDraftSteps);
        return reservationDraftStepsMapper.reservationDraftStepsToReservationDraftStepsDto(saveReservationDraftSteps);
    }
}
