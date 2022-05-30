package com.dw.ngms.cis.mappers;

import com.dw.ngms.cis.dto.SearchTypeOfficeMappingDto;
import com.dw.ngms.cis.persistence.domain.SearchTypeOfficeMapping;
import org.mapstruct.Mapper;

/**
 * @author : prateekgoel
 * @since : 22/05/21, Sat
 **/
@Mapper(componentModel = "spring")
public interface SearchTypeOfficeMappingMapper {

    SearchTypeOfficeMappingDto
    searchTypeOfficeMappingToSearchTypeOfficeMappingDto(SearchTypeOfficeMapping searchTypeOfficeMapping);

    SearchTypeOfficeMapping
    searchTypeOfficeMappingDtoToSearchTypeOfficeMapping(SearchTypeOfficeMappingDto searchTypeOfficeMappingDto);

}
