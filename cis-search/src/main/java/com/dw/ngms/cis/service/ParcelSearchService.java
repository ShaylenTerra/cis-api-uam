package com.dw.ngms.cis.service;

import com.dw.ngms.cis.dto.SgdataParcelsDto;
import com.dw.ngms.cis.enums.SearchContext;
import com.dw.ngms.cis.mappers.SgDataParcelsMapper;
import com.dw.ngms.cis.persistence.projection.parcel.ParcelErfProjection;
import com.dw.ngms.cis.persistence.projection.parcel.ParcelFarmProjection;
import com.dw.ngms.cis.persistence.projection.parcel.ParcelProjection;
import com.dw.ngms.cis.persistence.repository.SgdataParcelsRepository;
import com.dw.ngms.cis.utilities.SearchUtils;
import com.dw.ngms.cis.vms.ParcelSearchVm;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author : prateekgoel
 * @since : 08/01/21, Fri
 **/
@Service
@Slf4j
@AllArgsConstructor
public class ParcelSearchService {

    private final UserSearchHistoryService userSearchHistoryService;

    private final SgdataParcelsRepository sgdataParcelsRepository;

    private final SgDataParcelsMapper sgDataParcelsMapper;

    /**
     * @param parcelSearchVm {@link ParcelSearchVm}
     * @return Collection<SgdataNumberSearchFarmProjection>
     */
    public Page<ParcelFarmProjection> getParcelDescriptionFarmSearch(final ParcelSearchVm parcelSearchVm, final Pageable pageable) {

        userSearchHistoryService.storeUserSearchData(parcelSearchVm, SearchContext.PARCEL_DESCRIPTION);
        return sgdataParcelsRepository.findUsingParcelFarm(parcelSearchVm.getProvinceId(),
                parcelSearchVm.getTownship(),
                parcelSearchVm.getFarmName(),
                SearchUtils.padStringWithZero(parcelSearchVm.getParcelNumber(), 8),
                SearchUtils.padStringWithZero(parcelSearchVm.getPortion(), 5),
                pageable);
    }

    /**
     * @param parcelSearchVm {@link ParcelSearchVm}
     * @return Collection<SgdataNumberSearchProjection>
     */
    public Page<ParcelErfProjection> getParcelDescriptionUsingErf(final ParcelSearchVm parcelSearchVm, final Pageable pageable) {
        userSearchHistoryService.storeUserSearchData(parcelSearchVm, SearchContext.PARCEL_DESCRIPTION);
        return sgdataParcelsRepository.findUsingParcelErf(parcelSearchVm.getProvinceId(),
                parcelSearchVm.getMunicipalityCode(),
                parcelSearchVm.getTownship(),
                SearchUtils.padStringWithZero(parcelSearchVm.getParcelNumber(), 8),
                SearchUtils.padStringWithZero(parcelSearchVm.getPortion(), 5),
                pageable);
    }

    /**
     * @param parcelSearchVm {@link ParcelSearchVm}
     * @return Collection<SgdataNumberSearchProjection>
     */
    public Page<ParcelErfProjection> getParcelDescriptionUsingHoldings(final ParcelSearchVm parcelSearchVm, final Pageable pageable) {
        userSearchHistoryService.storeUserSearchData(parcelSearchVm, SearchContext.PARCEL_DESCRIPTION);
        return sgdataParcelsRepository.findUsingParcelHolding(parcelSearchVm.getProvinceId(),
                parcelSearchVm.getTownship(),
                SearchUtils.padStringWithZero(parcelSearchVm.getParcelNumber(), 8),
                SearchUtils.padStringWithZero(parcelSearchVm.getPortion(), 5),
                pageable);
    }

    /**
     * @param parcelSearchVm {@link ParcelSearchVm}
     * @return Collection<SgdataNumberSearchProjection>
     */
    public Page<ParcelErfProjection> getParcelDescriptionUsingLpi(final ParcelSearchVm parcelSearchVm, final Pageable pageable) {
        userSearchHistoryService.storeUserSearchData(parcelSearchVm, SearchContext.PARCEL_DESCRIPTION);
        return sgdataParcelsRepository.findUsgParcelLpi(parcelSearchVm.getProvinceId(),
                parcelSearchVm.getMunicipalityCode(),
                parcelSearchVm.getLpi(), pageable);
    }

    /**
     * @param recordId recordId
     * @return Collection<SgdataNumberSearchProjection>
     */
    public Page<SgdataParcelsDto> getDataProfileUsingOnlyLpi(final Long recordId, final Pageable pageable) {
        return sgdataParcelsRepository.findDataProfileUsingRecordId(recordId, pageable)
                .map(sgDataParcelsMapper::sgdataParcelsToSgdataParcelsDto);
    }





}
