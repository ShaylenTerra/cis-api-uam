package com.dw.ngms.cis.service.mapper.reservation;

import com.dw.ngms.cis.persistence.domains.reservation.ReservationOutcome;
import com.dw.ngms.cis.service.dto.reservation.ReservationOutcomeDto;
import org.mapstruct.*;

/**
 * @author prateek on 12-04-2022
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
@DecoratedWith(ReservationOutcomeDecorator.class)
public interface ReservationOutcomeMapper {


    @Mappings({
            @Mapping(target = "locationId", source = "locationId"),
            @Mapping(target = "lpi", source = "lpi"),
            @Mapping(target = "requestId", source = "requestId"),
            @Mapping(target = "designation", source = "designation"),
            @Mapping(target = "draftId", source = "draftId"),
            @Mapping(target = "reasonItemId", source = "reasonItemId"),
            @Mapping(target = "statusItemId", source = "statusItemId"),
            @Mapping(target = "workflowId", source = "workflowId"),
            @Mapping(target = "portion", source = "portion"),
            @Mapping(target = "parcel", source = "parcel"),
            @Mapping(target = "algorithm", source = "algorithm"),
            @Mapping(target = "expiryDate", source = "expiryDate"),
            @Mapping(target = "issueDate", source = "issueDate"),
            @Mapping(target = "outcomeId", source = "outcomeId"),
            @Mapping(target = "ownerUserId", source = "ownerUserId"),
            @Mapping(target = "locationName", source = "locationName")

    })
    ReservationOutcomeDto reservationOutcomeToReservationOutcomeDto(ReservationOutcome reservationOutcome);

    @InheritInverseConfiguration
    ReservationOutcome reservationOutcomeDtoToReservationOutcome(ReservationOutcomeDto reservationOutcomeDto);

}
