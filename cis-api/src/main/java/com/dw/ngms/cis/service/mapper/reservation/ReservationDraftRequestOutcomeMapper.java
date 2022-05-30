package com.dw.ngms.cis.service.mapper.reservation;

import com.dw.ngms.cis.persistence.domains.reservation.ReservationDraftRequestOutcome;
import com.dw.ngms.cis.service.dto.reservation.ReservationDraftRequestOutcomeDto;
import org.mapstruct.*;

/**
 * @author prateek on 03-01-2022
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
@DecoratedWith(ReservationDraftRequestOutcomeDecorator.class)
public interface ReservationDraftRequestOutcomeMapper {

    @Mappings({
            @Mapping(target = "designation", source = "designation"),
            @Mapping(target = "draftOutcomeId", source = "draftOutcomeId"),
            @Mapping(target = "locationId", source = "locationId"),
            @Mapping(target = "parcel", source = "parcel"),
            @Mapping(target = "portion", source = "portion"),
            @Mapping(target = "location", source = "location"),
            @Mapping(target = "recordTypeId", source = "recordTypeId"),
            @Mapping(target = "lpi", source = "lpi"),
            @Mapping(target = "stepId", ignore = true)

    })
    ReservationDraftRequestOutcomeDto
    reservationDraftRequestOutcomeToReservationDraftRequestOutcomeDto(ReservationDraftRequestOutcome
                                                                              reservationDraftRequestOutcome);

    @InheritInverseConfiguration
    ReservationDraftRequestOutcome
    reservationDraftRequestOutcomeDtoToReservationDraftRequestOutcome(ReservationDraftRequestOutcomeDto
                                                                              reservationDraftRequestOutcomeDto);
}
