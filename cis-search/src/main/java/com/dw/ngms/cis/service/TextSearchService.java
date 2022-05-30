package com.dw.ngms.cis.service;

import com.dw.ngms.cis.dto.SgdataParcelsDto;
import com.dw.ngms.cis.enums.SearchContext;
import com.dw.ngms.cis.mappers.SgDataParcelsMapper;
import com.dw.ngms.cis.persistence.repository.SgdataParcelsRepository;
import com.dw.ngms.cis.vms.TextSearchVm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author : prateekgoel
 * @since : 19/01/21, Tue
 **/
@Service
@AllArgsConstructor
@Slf4j
public class TextSearchService {

    private final UserSearchHistoryService userSearchHistoryService;

    private final SgdataParcelsRepository sgdataParcelsRepository;

    private final SgDataParcelsMapper sgDataParcelsMapper;

    /**
     * @param textSearchVm {@link TextSearchVm}
     * @return Collection<SgdataNumberSearchProjection>
     */
    public Page<SgdataParcelsDto> findByTextSearch(final TextSearchVm textSearchVm,
                                                   final Pageable pageable) {
        log.debug(" text search for vm {} ", textSearchVm);
        userSearchHistoryService.storeUserSearchData(textSearchVm, SearchContext.TEXT_SEARCH);
        return sgdataParcelsRepository.findUsingTextSearch(textSearchVm.getSgNo(),
                textSearchVm.getParcel(),
                textSearchVm.getProvinceId(),
                textSearchVm.getLocalMunicipalityName(),
                textSearchVm.getRegion(),
                textSearchVm.getLpi(),
                pageable).map(sgDataParcelsMapper::sgdataParcelsToSgdataParcelsDto);
    }


}
