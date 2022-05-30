package com.dw.ngms.cis.service.mapper;

import com.dw.ngms.cis.persistence.domains.fee.FeeSubCategory;
import com.dw.ngms.cis.service.dto.fee.FeeSubCategoryDto;
import org.mapstruct.*;

import java.util.Collection;

/**
 * @author : prateekgoel
 * @since : 27/11/20, Fri
 **/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FeeSubCategoryMapper {

    @Mappings({
            @Mapping(target = "feeSubCategoryId", source = "feeSubCategoryId"),
            @Mapping(target = "name", source = "name"),
            @Mapping(target = "description", source = "description"),
            @Mapping(target = "isActive", source = "isActive"),
            @Mapping(target = "categoryId", source = "categoryId")
    })
    FeeSubCategory feeSubCategoryDtoToFeeSubCategory(FeeSubCategoryDto feeSubCategoryDto);

    @InheritInverseConfiguration
    FeeSubCategoryDto feeSubCategoryToFeeSubCategoryDto(FeeSubCategory feeSubCategory);

    Collection<FeeSubCategory>
    feeSubCategoryDtoCollectionToFeeSubCategoryCollection(Collection<FeeSubCategoryDto> feeSubCategoryDtos);

    Collection<FeeSubCategoryDto>
    feeSubCategoryCollectionToFeeSubCategoryDtoCollection(Collection<FeeSubCategory> feeSubCategories);

}
