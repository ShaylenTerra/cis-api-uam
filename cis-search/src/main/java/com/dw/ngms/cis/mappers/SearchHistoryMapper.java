package com.dw.ngms.cis.mappers;

import com.dw.ngms.cis.dto.SearchHistoryDto;
import com.dw.ngms.cis.persistence.domain.SearchUserLog;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * @author : prateekgoel
 * @since : 10/03/21, Wed
 **/
@Mapper(componentModel = "spring")
public interface SearchHistoryMapper {

    @Mappings({
            @Mapping(target = "provinceId", source = "provinceId"),
            @Mapping(target = "userId", source = "userId"),
            @Mapping(target = "searchFilterId", source = "searchFilterId"),
            @Mapping(target = "searchTypeId", source = "searchTypeId"),
            @Mapping(target = "data", source = "data"),
            @Mapping(target = "caption", source = "caption")
    })
    SearchHistoryDto searchUserLogToSearchHistoryDto(SearchUserLog searchUserLog);

    @InheritInverseConfiguration
    SearchUserLog searchHistoryDtoToSearchUserLog(SearchHistoryDto searchHistoryDto);
}
