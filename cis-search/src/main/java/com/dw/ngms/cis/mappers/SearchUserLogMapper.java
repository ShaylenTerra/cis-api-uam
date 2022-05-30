package com.dw.ngms.cis.mappers;

import com.dw.ngms.cis.dto.SearchUserLogDto;
import com.dw.ngms.cis.persistence.domain.SearchUserLog;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SearchUserLogMapper {

    SearchUserLog searchUserLogDtoToSearchUserLog(SearchUserLogDto searchUserLogDto);

    SearchUserLogDto searchUserLogToSearchUserLogDto(SearchUserLog searchUserLog);

}
