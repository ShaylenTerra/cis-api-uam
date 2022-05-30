package com.dw.ngms.cis.service.mapper.lodgment;

import com.dw.ngms.cis.persistence.domains.lodgement.LodgementDraftDocument;
import com.dw.ngms.cis.service.dto.lodgment.LodgementDraftDocumentDto;
import org.mapstruct.*;

import java.time.LocalDateTime;

/**
 * @author prateek on 12-04-2022
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        imports = {
                LocalDateTime.class
        }
)
@DecoratedWith(LodgementDraftDocumentDecorator.class)
public interface LodgementDraftDocumentMapper {

    @Mappings({
            @Mapping(target = "documentItemId", source = "documentItemId"),
            @Mapping(target = "documentId", source = "documentId"),
            @Mapping(target = "dated", source = "dated", defaultExpression = "java(LocalDateTime.now())"),
            @Mapping(target = "notes", source = "notes"),
            @Mapping(target = "userId", source = "userId"),
            @Mapping(target = "documentName", source = "documentName"),
            @Mapping(target = "purposeItemId", source = "purposeItemId")
    })
    LodgementDraftDocument lodgementDraftDocumentDtoToLodgementDraftDocument(LodgementDraftDocumentDto lodgementDraftDocumentDto);

    @InheritInverseConfiguration
    LodgementDraftDocumentDto lodgementDraftDocumentToLodgementDraftDocumentDto(LodgementDraftDocument lodgementDraftDocument);

}
