package com.dw.ngms.cis.service;

import com.dw.ngms.cis.enums.SearchContext;
import com.dw.ngms.cis.persistence.projection.number.*;
import com.dw.ngms.cis.persistence.repository.SgdataParcelsRepository;
import com.dw.ngms.cis.vms.NumberSearchVm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author : prateekgoel
 * @since : 07/01/21, Thu
 **/
@Service
@Slf4j
@AllArgsConstructor
public class NumberSearchService {

    private final SgdataParcelsRepository sgdataParcelsRepository;

    private final UserSearchHistoryService userSearchHistoryService;


    /**
     * @param numberSearchVm searchQueryVm
     * @return Collection<SgNumbersProjection>
     */
    public Page<NumberProjection> searchNumberBySgNo(NumberSearchVm numberSearchVm, Pageable pageable) {

        userSearchHistoryService.storeUserSearchData(numberSearchVm, SearchContext.NUMBER);
        return sgdataParcelsRepository.findUsingSgNumberAndProvinceId(numberSearchVm.getSgNo(),
                numberSearchVm.getProvinceId()
                , pageable);

    }


    public NumberProjection searchBySgNo(final String sgNumber, final Long provinceId) {
        Page<NumberProjection> usingSgNumberAndProvinceId = sgdataParcelsRepository
                .findUsingSgNumberAndProvinceId(sgNumber, provinceId, Pageable.unpaged());
        if(null != usingSgNumberAndProvinceId && CollectionUtils.isNotEmpty(usingSgNumberAndProvinceId.getContent())){
            return usingSgNumberAndProvinceId.stream().findFirst().get();
        }
        return null;
    }


    /**
     * getting summary data for compilationNumber search
     *
     * @param numberSearchVm searchQueryVm
     * @return Collection<CompilationNumberProjection> {@link CompilationNumberProjection}
     */
    public Page<CompilationNumberProjection> searchByCompilationNumber(final NumberSearchVm numberSearchVm, final Pageable pageable) {

        userSearchHistoryService.storeUserSearchData(numberSearchVm, SearchContext.NUMBER);

        return sgdataParcelsRepository.findUsingCompilationNoAndProvinceId(numberSearchVm.getCompilationNo(),
                numberSearchVm.getProvinceId(), pageable);
    }

    /**
     * getting summary data for filingNumber
     *
     * @param numberSearchVm searchQueryVm
     * @return Collection<FilingProjection> {@link FilingProjection}
     */
    public Page<FilingProjection> searchByFilingNumber(final NumberSearchVm numberSearchVm, final Pageable pageable) {

        userSearchHistoryService.storeUserSearchData(numberSearchVm, SearchContext.NUMBER);

        return sgdataParcelsRepository.findUsingFillingNoAndProvinceId(numberSearchVm.getFilingNo(),
                numberSearchVm.getProvinceId(), pageable
        );
    }


    /**
     * getting summary data for deed number
     *
     * @param numberSearchVm searchQueryVm
     * @return Collection<DeedNumberProjection> {@link DeedNumberProjection}
     */
    public Page<DeedNumberProjection> searchByDeedNumber(final NumberSearchVm numberSearchVm, final Pageable pageable) {

        userSearchHistoryService.storeUserSearchData(numberSearchVm, SearchContext.NUMBER);


        return sgdataParcelsRepository.findUsingDeedNoAndProvinceId(numberSearchVm.getDeedNo(),
                numberSearchVm.getProvinceId(),
                pageable
        );
    }

    /**
     * @param numberSearchVm searchQueryVm
     * @return Collection<LeaseNumberProjection> {@link LeaseNumberProjection}
     */
    public Page<LeaseNumberProjection> searchByLeaseNumber(final NumberSearchVm numberSearchVm, final Pageable pageable) {

        userSearchHistoryService.storeUserSearchData(numberSearchVm, SearchContext.NUMBER);

        return sgdataParcelsRepository.findUsingLeaseNoAndProvinceId(numberSearchVm.getLeaseNo(),
                numberSearchVm.getProvinceId(),
                pageable
        );
    }

    /**
     * @param numberSearchVm searchQueryVm
     * @return Collection<SurveyRecordsProjection> {@link SurveyRecordsProjection}
     */
    public Page<SurveyRecordsProjection> searchBySurveyRecordNumber(final NumberSearchVm numberSearchVm, final Pageable pageable) {

        userSearchHistoryService.storeUserSearchData(numberSearchVm, SearchContext.NUMBER);

        return sgdataParcelsRepository.findUsingSurveyRecordNoAndProvinceId(numberSearchVm.getSurveyRecordNo(),
                numberSearchVm.getProvinceId(),
                pageable
        );
    }

}
