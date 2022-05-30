package com.dw.ngms.cis.service.mapper.reservation;

import com.dw.ngms.cis.persistence.domains.reservation.ReservationDraftSteps;
import com.dw.ngms.cis.service.dto.reservation.ReservationDraftStepsDto;
import org.mapstruct.*;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {
                ReservationDraftRequestMapper.class,
                ReservationDraftRequestOutcomeMapper.class
        }
)
@DecoratedWith(ReservationDraftStepsMapperDecorator.class)
public interface ReservationDraftStepsMapper {
    @Mappings({
            @Mapping(target = "stepId", source = "stepId"),
            @Mapping(target = "stepNo", source = "stepNo"),
            @Mapping(target = "reasonItemId", source = "reasonItemId"),
            @Mapping(target = "parcelRequested", source = "parcelRequested"),
            @Mapping(target = "otherData", source = "otherData"),
            @Mapping(target = "reservationDraftRequestDto", source = "reservationDraftRequests"),
            @Mapping(target = "reservationDraftRequestOutcomeDto", source = "reservationDraftRequestOutcomes"),
            @Mapping(target = "reservationOutcomeDto", source = "reservationOutcomes")
    })
    ReservationDraftStepsDto reservationDraftStepsToReservationDraftStepsDto(ReservationDraftSteps reservationDraftSteps);

    @InheritInverseConfiguration
    ReservationDraftSteps reservationDraftStepsDtoToReservationDraftSteps(ReservationDraftStepsDto reservationDraftStepsDto);

    ReservationDraftSteps reservationDraftStepsToReservationDraftSteps(ReservationDraftSteps reservationDraftSteps);
}
