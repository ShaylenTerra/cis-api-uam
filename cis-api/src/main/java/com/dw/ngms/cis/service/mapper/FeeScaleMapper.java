package com.dw.ngms.cis.service.mapper;

import com.dw.ngms.cis.persistence.domains.fee.FeeScale;
import com.dw.ngms.cis.service.dto.fee.FeeScaleDto;
import org.mapstruct.*;

/**
 * @author : prateekgoel
 * @since : 27/11/20, Fri
 **/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FeeScaleMapper {
    @Mappings({
            @Mapping(target = "feeScaleId", source = "feeScaleId"),
            @Mapping(target = "feeScaleName", source = "feeScaleName"),
            @Mapping(target = "userId", source = "userId"),
            @Mapping(target = "effectiveDate", source = "effectiveDate"),
            @Mapping(target = "description", source = "description"),
            @Mapping(target = "fileName", source = "documentName")
    })
    FeeScaleDto feeScaleToFeeScaleDto(FeeScale feeScale);

    @InheritInverseConfiguration
    FeeScale feeScaleDtoToFeeScale(FeeScaleDto feeScaleDto);
}

