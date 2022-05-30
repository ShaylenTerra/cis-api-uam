package com.dw.ngms.cis.service.mapper.lodgment;

import com.dw.ngms.cis.persistence.domains.lodgement.LodgementDraftStep;
import com.dw.ngms.cis.service.dto.lodgment.LodgementDraftStepsDto;
import org.mapstruct.*;

/**
 * @author prateek on 06-04-2022
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {
            LodgementDraftRequestMapper.class
        }
)
@DecoratedWith(LodgementDraftStepsDecorator.class)
public interface LodgementDraftStepsMapper {

    @InheritInverseConfiguration
    LodgementDraftStepsDto lodgmentDraftStepsToLodgmentDraftStepsDto(LodgementDraftStep lodgementDraftStep);

    @Mappings({
            @Mapping(target = "stepId", source = "stepId"),
            @Mapping(target = "stepNo", source = "stepNo"),
            @Mapping(target = "purposeItemId", source = "reasonItemId"),
            @Mapping(target = "documentItemId", source = "documentItemId"),
            @Mapping(target = "lodgementDraftRequests", source = "lodgementDraftRequestDtos")

    })
    LodgementDraftStep lodgmentDraftStepsDtoToLodgmentDraftSteps(LodgementDraftStepsDto lodgementDraftStepsDto);
}
