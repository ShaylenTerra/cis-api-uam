package com.dw.ngms.cis.service;

import com.dw.ngms.cis.enums.SearchContext;
import com.dw.ngms.cis.persistence.projection.parcel.ParcelErfProjection;
import com.dw.ngms.cis.persistence.projection.parcel.ParcelFarmProjection;
import com.dw.ngms.cis.persistence.repository.SgdataParcelsRepository;
import com.dw.ngms.cis.utilities.SearchUtils;
import com.dw.ngms.cis.vms.RangeSearchVm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author : prateekgoel
 * @since : 10/01/21, Sun
 **/
@Service
@Slf4j
@AllArgsConstructor
public class RangeSearchService {

    private final SgdataParcelsRepository sgdataParcelsRepository;

    private final UserSearchHistoryService userSearchHistoryService;


    /**
     * @param rangeSearchVm {@link RangeSearchVm}
     * @return Collection<SgdataNumberSearchFarmProjection>
     */
    public Page<ParcelFarmProjection> getSearchDataBasedOnFarmRange(final RangeSearchVm rangeSearchVm,
                                                                    final Pageable pageable) {

        userSearchHistoryService.storeUserSearchData(rangeSearchVm, SearchContext.RANGE_SEARCH);
        return sgdataParcelsRepository.findFarmUsingRange(rangeSearchVm.getProvinceId(),
                rangeSearchVm.getTownship(),
                rangeSearchVm.getFarmName(),
                SearchUtils.padStringWithZero(rangeSearchVm.getParcel(), 8),
                SearchUtils.padStringWithZero(rangeSearchVm.getPortionFrom(), 5),
                SearchUtils.padStringWithZero(rangeSearchVm.getPortionTo(), 5),
                pageable);
    }

    /**
     * @param rangeSearchVm {@link RangeSearchVm}
     * @return Collection<SgdataNumberSearchProjection>
     */
    public Page<ParcelErfProjection> getSearchDataBasedOnErfPortionRange(final RangeSearchVm rangeSearchVm,
                                                                         final Pageable pageable) {

        userSearchHistoryService.storeUserSearchData(rangeSearchVm, SearchContext.RANGE_SEARCH);

        return sgdataParcelsRepository.findErfUsingPortionNoRange(rangeSearchVm.getProvinceId(),
                rangeSearchVm.getTownship(),
                SearchUtils.padStringWithZero(rangeSearchVm.getParcel(), 8),
                SearchUtils.padStringWithZero(rangeSearchVm.getPortionFrom(), 5),
                SearchUtils.padStringWithZero(rangeSearchVm.getPortionTo(), 5),
                pageable);
    }

    /**
     * @param rangeSearchVm {@link RangeSearchVm}
     * @return Collection<SgdataNumberSearchProjection>
     */
    public Page<ParcelErfProjection> getSearchDataBasedOnErfParcelRange(final RangeSearchVm rangeSearchVm,
                                                                        final Pageable pageable) {

        userSearchHistoryService.storeUserSearchData(rangeSearchVm, SearchContext.RANGE_SEARCH);

        return sgdataParcelsRepository.findErfUsingParcelRange(rangeSearchVm.getProvinceId(),
                rangeSearchVm.getTownship(),
                rangeSearchVm.getSgNo(),
                SearchUtils.padStringWithZero(rangeSearchVm.getParcelFrom(), 8),
                SearchUtils.padStringWithZero(rangeSearchVm.getParcelTo(), 8),
                pageable);
    }

    public Page<ParcelErfProjection> getSearchDataBasedOnHoldingParcelRange(final RangeSearchVm rangeSearchVm,
                                                                                final Pageable pageable) {
        userSearchHistoryService.storeUserSearchData(rangeSearchVm, SearchContext.RANGE_SEARCH);
        return sgdataParcelsRepository.findHoldingUsingParcelRange(rangeSearchVm.getProvinceId(),
                rangeSearchVm.getTownship(),
                rangeSearchVm.getMunicipalityCode(),
                SearchUtils.padStringWithZero(rangeSearchVm.getParcelFrom(), 8),
                SearchUtils.padStringWithZero(rangeSearchVm.getParcelTo(), 8),
                pageable);
    }

    public Page<ParcelErfProjection> getSearchDataBasedOnHoldingPortionRange(final RangeSearchVm rangeSearchVm,
                                                                            final Pageable pageable) {
        userSearchHistoryService.storeUserSearchData(rangeSearchVm, SearchContext.RANGE_SEARCH);
        return sgdataParcelsRepository.findHoldingUsingPortionRange(rangeSearchVm.getProvinceId(),
                rangeSearchVm.getTownship(),
                SearchUtils.padStringWithZero(rangeSearchVm.getPortionFrom(), 5),
                SearchUtils.padStringWithZero(rangeSearchVm.getPortionTo(), 5),
                SearchUtils.padStringWithZero(rangeSearchVm.getParcel(), 8),
                pageable);
    }


}
