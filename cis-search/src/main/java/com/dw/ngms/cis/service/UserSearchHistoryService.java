package com.dw.ngms.cis.service;

import com.dw.ngms.cis.dto.SearchUserLogDto;
import com.dw.ngms.cis.enums.SearchContext;
import com.dw.ngms.cis.mappers.SearchUserLogMapper;
import com.dw.ngms.cis.persistence.domain.SearchUserLog;
import com.dw.ngms.cis.persistence.repository.SearchUserLogRepository;
import com.dw.ngms.cis.vms.SearchVm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author : prateekgoel
 * @since : 12/01/21, Tue
 **/
@Service
@Slf4j
@AllArgsConstructor
public class UserSearchHistoryService {

    private static final String BLANK = " ";
    private final SearchUserLogRepository searchUserLogRepository;
    private final SearchUserLogMapper searchUserLogMapper;
    private final ObjectMapper mapper;

    public void storeUserSearchData(SearchVm searchVm, SearchContext searchContext) {


        try {
            SearchUserLogDto searchUserLogDto = SearchUserLogDto.builder()
                    .userId(searchVm.getUserId())
                    .provinceId(searchVm.getProvinceId())
                    .searchTypeId(searchVm.getSearchTypeId())
                    .caption(searchContext.getSearchContext() + BLANK + searchVm.getProvinceShortName())
                    .searchFilterId(searchVm.getSearchFilterId())
                    .data(mapper.writeValueAsString(searchVm))
                    .build();

            SearchUserLog searchUserLog = searchUserLogMapper.searchUserLogDtoToSearchUserLog(searchUserLogDto);

            log.debug(" saving user search log {}  ", searchUserLog);

            searchUserLog.setDated(LocalDateTime.now());

            searchUserLogRepository.save(searchUserLog);

        } catch (Exception e) {
            log.error(" error while storing user search {} log with cause {}", searchVm, e.getMessage());
            e.printStackTrace();
        }


    }

}
