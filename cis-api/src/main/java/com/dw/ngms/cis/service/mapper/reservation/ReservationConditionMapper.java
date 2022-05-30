package com.dw.ngms.cis.service.mapper.reservation;

import com.dw.ngms.cis.persistence.domains.reservation.ReservationCondition;
import com.dw.ngms.cis.service.dto.reservation.ReservationConditionDto;
import org.mapstruct.Mapper;

/**
 * @author prateek on 03-02-2022
 */
@Mapper(componentModel = "spring")
public interface ReservationConditionMapper {

    ReservationConditionDto reservationConditionToReservationConditionDto(ReservationCondition reservationCondition);

    ReservationCondition reservationConditionDtoToReservationCondition(ReservationConditionDto reservationConditionDto);

}
