package com.dw.ngms.cis.service.mapper.lodgment;

import com.dw.ngms.cis.persistence.domains.lodgement.LodgementBatch;
import com.dw.ngms.cis.service.dto.lodgment.LodgementBatchDto;
import com.dw.ngms.cis.service.dto.lodgment.LodgementBatchSgDocumentDto;
import org.mapstruct.*;

import java.time.LocalDateTime;

/**
 * @author prateek on 21-04-2022
 */
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        imports = {LocalDateTime.class},
        uses = {LodgementBatchSgDocumentMapper.class}
)
@DecoratedWith(LodgementBatchDecorator.class)
public interface LodgementBatchMapper {


    @Mappings({
            @Mapping(target = "batchId", source = "batchId"),
            @Mapping(target = "batchNumber", source = "batchNumber"),
            @Mapping(target = "batchNumberText", source = "batchNumberText"),
            @Mapping(target = "provinceId", source = "provinceId"),
            @Mapping(target = "userId", source = "userId"),
            @Mapping(target = "createdOn", source = "createdOn", defaultExpression = "java(LocalDateTime.now())"),
            @Mapping(target = "lodgementBatchSgDocuments", source = "lodgementBatchSgDocumentDtos"),

    })
    LodgementBatch lodgementBatchDtoToLodgementBatch(LodgementBatchDto lodgementBatchDto);


    @InheritInverseConfiguration
    LodgementBatchDto lodgementBatchToLodgementBatchDto(LodgementBatch lodgementBatch);

}
