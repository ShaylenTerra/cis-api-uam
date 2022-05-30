package com.dw.ngms.cis.service.mapper.lodgment;

import com.dw.ngms.cis.persistence.domains.lodgement.LodgementDraftAnnexure;
import com.dw.ngms.cis.service.dto.lodgment.LodgementDraftAnnexureDto;
import org.mapstruct.*;

import java.time.LocalDateTime;

/**
 * @author prateek on 06-04-2022
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        imports = {LocalDateTime.class}
)
@DecoratedWith(LodgementDraftAnnexureDecorator.class)
public interface LodgementDraftAnnexureMapper {


    @InheritInverseConfiguration
    LodgementDraftAnnexureDto lodgementDraftAnnexureToLodgementDraftAnnexureDto(LodgementDraftAnnexure lodgementDraftAnnexure);

    @Mappings({
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "annexureId", source = "annexureId"),
            @Mapping(target = "userId", source = "userId"),
            @Mapping(target = "notes", source = "notes"),
            @Mapping(target = "typeItemId", source = "typeItemId"),
            @Mapping(target = "dated", source = "dated",  defaultExpression = "java(LocalDateTime.now())")

    })
    LodgementDraftAnnexure lodgementDraftAnnexureDtoToLodgementDraftAnnexure(LodgementDraftAnnexureDto lodgementDraftAnnexureDto);

}
