package com.dw.ngms.cis.service.mapper.reservation;

import com.dw.ngms.cis.persistence.domains.reservation.ReservationDraft;
import com.dw.ngms.cis.service.dto.reservation.ReservationDraftDto;
import org.mapstruct.*;

import java.time.LocalDateTime;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        imports = {
                LocalDateTime.class
        },
        uses = {ReservationDraftDocumentsMapper.class,
                ReservationDraftStepsMapper.class}

)
@DecoratedWith(ReservationDraftDecorator.class)
public interface ReservationDraftMapper {

    @Mappings({
            @Mapping(target = "draftId", source = "draftId"),
            @Mapping(target = "userId", source = "userId"),
            @Mapping(target = "provinceId", source = "provinceId"),
            @Mapping(target = "dated", source = "dated", defaultExpression = "java(LocalDateTime.now())"),
            @Mapping(target = "purpose", source = "purpose"),
            @Mapping(target = "updatedOn",  expression = "java(LocalDateTime.now())"),
            @Mapping(target = "applicant", source = "applicant"),
            @Mapping(target = "applicantUserId", source = "applicantUserId"),
            @Mapping(target = "surveyDate", source = "surveyDate"),
            @Mapping(target = "deliveryMethod", source = "deliveryMethod"),
            @Mapping(target = "deliveryMethodItemId", source = "deliveryMethodItemId"),
            @Mapping(target = "workflowId", source = "workflowId"),
            @Mapping(target = "email", source = "email"),
            @Mapping(target = "isPrimaryEmail", source = "isPrimaryEmail"),
            @Mapping(target = "reservationDraftDocuments", source = "reservationDraftDocumentsDto"),
            @Mapping(target = "reservationDraftSteps", source = "reservationDraftStepsDto"),
            @Mapping(target = "toUserId", source = "toUserId"),
            @Mapping(target = "processId", source = "processId"),
            @Mapping(target = "fileRef", source = "fileRef")

    })
    ReservationDraft reservationDraftDtoToReservationDraft(ReservationDraftDto reservationDraftDto);

   @InheritInverseConfiguration
    ReservationDraftDto reservationDraftToReservationDraftDto(ReservationDraft reservationDraft);
}
