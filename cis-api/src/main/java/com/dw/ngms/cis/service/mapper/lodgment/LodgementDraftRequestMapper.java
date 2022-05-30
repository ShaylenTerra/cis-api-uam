package com.dw.ngms.cis.service.mapper.lodgment;

import com.dw.ngms.cis.persistence.domains.lodgement.LodgementDraftRequest;
import com.dw.ngms.cis.service.dto.lodgment.LodgementDraftRequestDto;
import org.mapstruct.*;

/**
 * @author prateek on 06-04-2022
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {
            LodgementDraftDocumentMapper.class
        }
)
@DecoratedWith(LodgementDraftRequestDecorator.class)
public interface LodgementDraftRequestMapper {

    @InheritInverseConfiguration
    LodgementDraftRequestDto lodgementDraftRequestToLodgementDraftRequestDto(LodgementDraftRequest lodgementDraftRequest);


    @Mappings({
            @Mapping(target = "designation", source = "designation"),
            @Mapping(target = "requestId", source = "requestId"),
            @Mapping(target = "lpi", source = "lpi"),
            @Mapping(target = "locationId", source = "locationId"),
            @Mapping(target = "outcomeIdReservation", source = "outcomeIdReservation"),
            @Mapping(target = "parentParcels", source = "parentParcels"),
            @Mapping(target = "reservationWorkflowId", source = "reservationWorkflowId"),
            @Mapping(target = "lodgementDraftDocuments", source = "lodgementDraftDocumentDtos"),
    })
    LodgementDraftRequest lodgementDraftRequestDtoToLodgementDraftRequest(LodgementDraftRequestDto lodgementDraftRequestDto);
}
