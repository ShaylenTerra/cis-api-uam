package com.dw.ngms.cis.service.mapper.reservation;

import com.dw.ngms.cis.persistence.domains.reservation.ReservationDraftRequest;
import com.dw.ngms.cis.service.dto.reservation.ReservationDraftRequestDto;
import org.mapstruct.*;

/**
 * @author prateek on 03-01-2022
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
@DecoratedWith(ReservationDraftRequestDecorator.class)
public interface ReservationDraftRequestMapper {

    @InheritInverseConfiguration
    ReservationDraftRequest reservationDraftRequestDtoToReservationDraftRequest(ReservationDraftRequestDto reservationDraftRequestDto);


    @Mappings({
            @Mapping(target = "recordId", source = "recordId"),
            @Mapping(target = "designation", source = "designation"),
            @Mapping(target = "draftRequestId", source = "draftRequestId"),
            @Mapping(target = "draftOutcomeId", source = "draftOutcomeId"),
            @Mapping(target = "locationId", source = "locationId"),
            @Mapping(target = "lpi", source = "lpi"),
            @Mapping(target = "parcel", source = "parcel"),
            @Mapping(target = "portion", source = "portion"),
            @Mapping(target = "recordTypeId", source = "recordTypeId"),
            @Mapping(target = "location", source = "location")

    })
    ReservationDraftRequestDto reservationDraftRequestToReservationDraftRequestDto(ReservationDraftRequest reservationDraftRequest);

}
