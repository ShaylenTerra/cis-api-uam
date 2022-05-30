package com.dw.ngms.cis.mappers;

import com.dw.ngms.cis.dto.SearchTypeDto;
import com.dw.ngms.cis.dto.SearchTypeOfficeMappingDto;
import com.dw.ngms.cis.persistence.domain.SearchType;
import com.dw.ngms.cis.persistence.domain.SearchTypeOfficeMapping;
import org.mapstruct.Mapper;

/**
 * @author : prateekgoel
 * @since : 10/12/20, Thu
 **/
@Mapper(componentModel = "spring")
public interface SearchMapper {

    SearchTypeDto searchTypeToSearchTypeDto(SearchType searchType);

    SearchType searchTypeDtoToSearchType(SearchTypeDto searchTypeDto);

    SearchTypeOfficeMappingDto searchTypeOfficeMappingToSearchTypeOfficeMappingDto(SearchTypeOfficeMapping searchTypeOfficeMapping);

    SearchTypeOfficeMapping searchTypeOfficeMappingDtoToSearchTypeOfficeMapping(SearchTypeOfficeMappingDto searchTypeOfficeMappingDto);
}
