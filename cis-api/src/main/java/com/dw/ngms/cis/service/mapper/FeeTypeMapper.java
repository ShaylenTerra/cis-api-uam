package com.dw.ngms.cis.service.mapper;

import com.dw.ngms.cis.persistence.domains.fee.FeeType;
import com.dw.ngms.cis.service.dto.fee.FeeTypeDto;
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
public interface FeeTypeMapper {

    @Mappings({
            @Mapping(target = "id", source = "typeId"),
            @Mapping(target = "feeType", source = "feeType"),
            @Mapping(target = "description", source = "description"),
            @Mapping(target = "isActive", source = "isActive"),
    })
    FeeTypeDto feeTypeToFeeTypeDto(FeeType feeType);

    @InheritInverseConfiguration
    FeeType feeTypeDtoToFeeType(FeeTypeDto feeTypeDto);

    Collection<FeeTypeDto> feeTypeCollectionToFeeTypeDtoCollection(Collection<FeeType> feeType);

    Collection<FeeType> feeTypeDtoCollectionToFeeTypeCollection(Collection<FeeTypeDto> feeTypeDtos);

}
