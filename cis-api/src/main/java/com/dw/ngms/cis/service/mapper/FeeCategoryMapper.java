package com.dw.ngms.cis.service.mapper;

import com.dw.ngms.cis.persistence.domains.fee.FeeCategory;
import com.dw.ngms.cis.service.dto.fee.FeeCategoryDto;
import org.mapstruct.*;

import java.util.Collection;

/**
 * @author : prateekgoel
 * @since : 27/11/20, Fri
 **/
@Mapper(componentModel = "spring",
        uses = {FeeSubCategoryMapper.class},
        unmappedTargetPolicy = ReportingPolicy.WARN)
public interface FeeCategoryMapper {

    @Mappings({
            @Mapping(target = "feeCategoryId", source = "feeCategoryId"),
            @Mapping(target = "description", source = "description"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "isActive", source = "isActive"),
    })
    FeeCategory feeCategoryDtoToFeeCategory(FeeCategoryDto feeCategoryDto);

    @InheritInverseConfiguration
    FeeCategoryDto feeCategoryToFeeCategoryDto(FeeCategory feeCategory);

    Collection<FeeCategory>
    feeCategoryDtoCollectionToFeeCategoryCollection(Collection<FeeCategoryDto> feeCategoryDtos);

    Collection<FeeCategoryDto>
    feeCategoryCollectionToFeeCategoryDtoCollection(Collection<FeeCategory> feeCategories);
}
