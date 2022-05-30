package com.dw.ngms.cis.service.mapper.lodgment;

import com.dw.ngms.cis.persistence.domains.lodgement.LodgementBatchSgDocument;
import com.dw.ngms.cis.service.dto.lodgment.LodgementBatchSgDocumentDto;
import org.mapstruct.*;

import java.time.LocalDateTime;

/**
 * @author prateek on 21-04-2022
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        imports = {LocalDateTime.class}
)
@DecoratedWith(LodgementBatchSgDocumentDecorator.class)
public interface LodgementBatchSgDocumentMapper {

    @Mappings({
            @Mapping(target = "sgDocumentId", source = "sgDocumentId"),
            @Mapping(target = "provinceId", source = "provinceId"),
            @Mapping(target = "createdOn", source = "createdOn", defaultExpression = "java(LocalDateTime.now())"),
            @Mapping(target = "docNumber", source = "docNumber"),
            @Mapping(target = "docTypeItemId", source = "docTypeItemId"),
            @Mapping(target = "docNumberText", source = "docNumberText"),
            @Mapping(target = "resOutcomeId", source = "resOutcomeId"),

    })
    LodgementBatchSgDocument lodgementBatchSgDocumentDtoToLodgementBatchSgDocument(LodgementBatchSgDocumentDto lodgementBatchSgDocumentDto);

    @InheritInverseConfiguration
    LodgementBatchSgDocumentDto lodgementBatchSgDocumentToLodgementBatchSgDocumentDto(LodgementBatchSgDocument lodgementBatchSgDocument);

}
