package com.dw.ngms.cis.service.mapper;

import com.dw.ngms.cis.persistence.domains.fee.FeeMaster;
import com.dw.ngms.cis.service.dto.fee.FeeMasterDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.Collection;

/**
 * @author : prateekgoel
 * @since : 27/11/20, Fri
 **/
@Mapper(componentModel = "spring")
public interface FeeMasterMapper {

    @Mappings({
            @Mapping(target = "feeId", source = "feeId"),
            @Mapping(target = "feeScaledId", source = "feeScaledId"),
            @Mapping(target = "feeSubCategoryId", source = "feeSubCategoryId"),
            @Mapping(target = "feeTypeId", source = "feeTypeId"),
            @Mapping(target = "fee", source = "fee"),
            @Mapping(target = "isActive", source = "isActive")

    })
    FeeMasterDto feeMasterToFeeMasterDto(FeeMaster feeMaster);

    @InheritInverseConfiguration
    FeeMaster feeMasterDtoToFeeMaster(FeeMasterDto feeMasterDto);

    Collection<FeeMasterDto>
    feeMasterCollectionToFeeMasterDtoCollection(Collection<FeeMaster> feeMaster);

    Collection<FeeMaster>
    feeMasterDtoCollectionToFeeMasterCollection(Collection<FeeMasterDto> feeMasterDto);

}
