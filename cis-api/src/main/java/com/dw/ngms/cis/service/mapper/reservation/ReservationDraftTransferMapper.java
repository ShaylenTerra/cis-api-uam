package com.dw.ngms.cis.service.mapper.reservation;

import com.dw.ngms.cis.persistence.domains.reservation.ReservationDraftTransfer;
import com.dw.ngms.cis.service.dto.reservation.ReservationDraftTransferDto;
import org.mapstruct.Mapper;

/**
 * @author prateek on 20-03-2022
 */
@Mapper(componentModel = "spring")
public interface ReservationDraftTransferMapper {

    ReservationDraftTransfer
    reservationDraftTransferDtoToReservationDraftTransfer(ReservationDraftTransferDto reservationDraftTransferDto);

    ReservationDraftTransferDto
    reservationDraftTransferToReservationDraftTransferDto(ReservationDraftTransfer reservationDraftTransfer);
}
