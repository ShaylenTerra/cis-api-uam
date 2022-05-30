package com.dw.ngms.cis.service.mapper.lodgment;

import com.dw.ngms.cis.persistence.domains.lodgement.LodgementBatch;
import com.dw.ngms.cis.persistence.domains.lodgement.LodgementDraft;
import com.dw.ngms.cis.service.dto.lodgment.LodgementDraftDto;
import org.mapstruct.*;

import java.time.LocalDateTime;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        imports = {
                LocalDateTime.class
        },
        uses = {LodgementDraftAnnexureMapper.class,
                LodgementDraftStepsMapper.class,
                LodgementDraftPaymentsMapper.class,
        }

)
@DecoratedWith(LodgementDraftDecorator.class)
public interface LodgementDraftMapper {

    @Mappings({
            @Mapping(target = "draftId", source = "draftId"),
            @Mapping(target = "userId", source = "userId"),
            @Mapping(target = "provinceId", source = "provinceId"),
            @Mapping(target = "dated", source = "dated", defaultExpression = "java(LocalDateTime.now())"),
            @Mapping(target = "description", source = "description"),
            @Mapping(target = "updatedOn",  expression = "java(LocalDateTime.now())"),
            @Mapping(target = "applicant", source = "applicant"),
            @Mapping(target = "applicantUserId", source = "applicantUserId"),
            @Mapping(target = "deliveryMethod", source = "deliveryMethod"),
            @Mapping(target = "deliveryMethodItemId", source = "deliveryMethodItemId"),
            @Mapping(target = "workflowId", source = "workflowId"),
            @Mapping(target = "email", source = "email"),
            @Mapping(target = "isPrimaryEmail", source = "isPrimaryEmail"),
            @Mapping(target = "lodgementDraftAnnexures", source = "lodgementDraftAnnexureDto"),
            @Mapping(target = "lodgementDraftStep", source = "lodgementDraftStepsDto"),
            @Mapping(target = "lodgementDraftPayments", source = "lodgementDraftPaymentsDto"),
            @Mapping(target = "processId", source = "processId"),
            @Mapping(target = "fileRef", source = "fileRef")

    })
    LodgementDraft lodgementDraftDtoToLodgementDraft(LodgementDraftDto reservationDraftDto);

   @InheritInverseConfiguration
   LodgementDraftDto lodgementDraftToLodgementDraftDto(LodgementDraft lodgementDraft);
}
