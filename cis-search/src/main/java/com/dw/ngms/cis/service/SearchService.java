package com.dw.ngms.cis.service;

import com.dw.ngms.cis.dto.SearchDocumentDto;
import com.dw.ngms.cis.dto.SearchHistoryDto;
import com.dw.ngms.cis.dto.SearchTypeDto;
import com.dw.ngms.cis.dto.SearchTypeOfficeMappingDto;
import com.dw.ngms.cis.enums.UserTypes;
import com.dw.ngms.cis.mappers.SearchDocumentMapper;
import com.dw.ngms.cis.mappers.SearchHistoryMapper;
import com.dw.ngms.cis.mappers.SearchMapper;
import com.dw.ngms.cis.mappers.SearchTypeOfficeMappingMapper;
import com.dw.ngms.cis.persistence.domain.*;
import com.dw.ngms.cis.persistence.domain.number.SgdataParcels;
import com.dw.ngms.cis.persistence.projection.SearchProfileRelatedData;
import com.dw.ngms.cis.persistence.projection.SearchTypeAndFilterProjection;
import com.dw.ngms.cis.persistence.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.stream.Collectors;

/**
 * @author : prateekgoel
 * @since : 07/12/20, Mon
 **/
@Service
@AllArgsConstructor
@Slf4j
public class SearchService {

    private final SearchTypeRepository searchTypeRepository;

    private final SearchTypeOfficeMappingRepository searchTypeOfficeMappingRepository;

    private final SearchMapper searchMapper;

    private final SearchUserLogRepository searchUserLogRepository;

    private final SearchHistoryMapper searchHistoryMapper;

    private final SgdataDocumentsRepository sgdataDocumentsRepository;

    private final SearchDocumentMapper searchDocumentMapper;

    private final SearchTypeOfficeMappingMapper searchTypeOfficeMappingMapper;

    private final SgdataParcelsRepository sgdataParcelsRepository;

    private final SearchDetailsShareRepository searchDetailsShareRepository;

    /**
     * @param parentSearchId parent searchId
     * @return Collection<SearchTypeDto>
     */
    public Collection<SearchTypeDto> getSearchConfig(final Long parentSearchId, final Pageable pageable) {
        return searchTypeRepository.findAllByParentSearchTypeIdAndIsActive(parentSearchId, 1L, pageable).stream()
                .map(searchMapper::searchTypeToSearchTypeDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * method to return userTypes for dropdown on front end using rest call
     *
     * @return Collection<String>
     */
    public Collection<String> getUserType() {
        return Arrays.stream(UserTypes.values()).map(UserTypes::getUserTypes)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * method returning searchTypeOfficeMapping based on searchTypeId
     *
     * @param searchTypeId searchType Id
     * @return SearchTypeOfficeMappingDto
     */
    public Collection<SearchTypeOfficeMappingDto> getAllSearchTypeOfficeMapping(final Long searchTypeId,
                                                                                final UserTypes userTypes) {

        return searchTypeOfficeMappingRepository.findAllBySearchTypeIdAndUserType(searchTypeId, userTypes).stream()
                .map(searchMapper::searchTypeOfficeMappingToSearchTypeOfficeMappingDto)
                .collect(Collectors.toCollection(LinkedList::new));

    }

    /**
     * method returning search by and search filter values
     *
     * @param provinceId provinceId
     * @param userTypes  userTypes
     * @return Collection<SearchTypeDto>
     */
    public Collection<SearchTypeAndFilterProjection> getSearchTypeByProvinceIdAndUserTypeAndParentSearchTypeId(
            final Long provinceId, final UserTypes userTypes, final Long parentSearchTypeId, final Pageable pageable) {
        return searchTypeOfficeMappingRepository.findSearchTypeByProvinceIdAndUserTypeAndParentSearchTypeId(provinceId,
                userTypes, parentSearchTypeId, pageable);
    }

    public Collection<SearchTypeDto> getSearchTypeByParentSearchTypeId(
            final Long parentSearchTypeId, final Pageable pageable) {
        return searchTypeRepository.findAllByParentSearchTypeIdAndIsActive(parentSearchTypeId, 1L, pageable).stream()
                .map(searchMapper::searchTypeToSearchTypeDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * @param searchTypeOfficeMappingDto {@link SearchTypeOfficeMappingDto}
     * @return boolean value representing if update is successful or not
     */
    @Transactional
    public Boolean updateSearchTypeOfficeMapping(final SearchTypeOfficeMappingDto searchTypeOfficeMappingDto) {
        SearchTypeOfficeMapping searchTypeOfficeMapping = searchTypeOfficeMappingRepository
                .findByProvinceIdAndSearchTypeIdAndUserType(searchTypeOfficeMappingDto.getProvinceId(),
                        searchTypeOfficeMappingDto.getSearchTypeId(),
                        searchTypeOfficeMappingDto.getUserType());
        if (null == searchTypeOfficeMapping) {
            final SearchTypeOfficeMapping searchTypeOfficeMapping1 = searchTypeOfficeMappingMapper
                    .searchTypeOfficeMappingDtoToSearchTypeOfficeMapping(searchTypeOfficeMappingDto);
            searchTypeOfficeMappingRepository.save(searchTypeOfficeMapping1);
            return true;
        } else {
            return 1 == searchTypeOfficeMappingRepository
                    .updateSearchTypeOfficeMapping(searchTypeOfficeMappingDto.getIsActive(),
                            searchTypeOfficeMappingDto.getProvinceId(),
                            searchTypeOfficeMappingDto.getSearchTypeId(),
                            searchTypeOfficeMappingDto.getUserType());
        }
    }


    /**
     * method to retrieve user search data
     *
     * @param pageable pageable
     * @return Page<SearchUserLog>
     */
    public Page<SearchUserLog> getUserSearchDataByUserId(Long userId, Pageable pageable) {
        return searchUserLogRepository.findByUserId(userId, pageable);
    }

    /**
     * @param searchHistoryDto {@link SearchHistoryDto}
     * @return {@link SearchHistoryDto}
     */
    public SearchHistoryDto persistSearchHistory(final SearchHistoryDto searchHistoryDto) {
        SearchUserLog searchUserLog = searchHistoryMapper.searchHistoryDtoToSearchUserLog(searchHistoryDto);
        searchUserLog.setDated(LocalDateTime.now());
        SearchUserLog save = searchUserLogRepository.save(searchUserLog);
        return searchHistoryMapper.searchUserLogToSearchHistoryDto(save);
    }

    /**
     * @param recordId recordId
     * @param pageable {@link Pageable}
     * @return Page<SearchDocumentDto>
     */
    public Page<SearchDocumentDto> getDocuments(final Long recordId, final Pageable pageable) {
        return sgdataDocumentsRepository.findByRecordId(recordId, pageable)
                .map(searchDocumentMapper::sgdataDocumentToSearchDocumentDto);
    }

    public Collection<SgdataDocuments> getDocuments(final Long recordId) {
        return sgdataDocumentsRepository.findByRecordId(recordId);
    }

    public Page<SearchProfileRelatedData> getRelatedDataByLpi(final String lpi, final Pageable pageable) {
        return sgdataParcelsRepository.findRelatedDataByLpi(lpi, pageable);
    }

    public SgdataParcels getSearchByRecordId(final Long recordId) {
        return sgdataParcelsRepository.findByRecordId(recordId);
    }

    public SearchDetailsShare saveSearchDetailShare(final String emailId, final Long userId) {
        SearchDetailsShare searchDetailsShare = new SearchDetailsShare();
        searchDetailsShare.setEmailAddress(emailId);
        searchDetailsShare.setDated(LocalDateTime.now());
        searchDetailsShare.setUserId(userId);
        return saveSearchDetailShare(searchDetailsShare);
    }

    private SearchDetailsShare saveSearchDetailShare(SearchDetailsShare searchDetailsShare) {
        if(null == searchDetailsShare)
            return null;

        return searchDetailsShareRepository.save(searchDetailsShare);
    }
}
