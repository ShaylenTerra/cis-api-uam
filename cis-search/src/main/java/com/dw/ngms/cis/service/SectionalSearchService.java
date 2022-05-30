package com.dw.ngms.cis.service;

import com.dw.ngms.cis.enums.SearchContext;
import com.dw.ngms.cis.persistence.projection.sectional.SectionalSearchErfProjection;
import com.dw.ngms.cis.persistence.projection.sectional.SectionalSearchFarmProjection;
import com.dw.ngms.cis.persistence.projection.sectional.SectionalSearchProjection;
import com.dw.ngms.cis.persistence.repository.SgdataParcelsRepository;
import com.dw.ngms.cis.utilities.SearchUtils;
import com.dw.ngms.cis.vms.SectionalSearchVm;
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
public class SectionalSearchService {

    private final UserSearchHistoryService userSearchHistoryService;

    private final SgdataParcelsRepository sgdataParcelsRepository;

    /**
     * @param sectionalSearchVm {@link SectionalSearchVm }
     * @return Collection<SectionalSearchProjection>
     */
    public Page<SectionalSearchProjection> getSectionalTitleSearch(final SectionalSearchVm sectionalSearchVm,
                                                                   final Pageable pageable) {

        userSearchHistoryService.storeUserSearchData(sectionalSearchVm, SearchContext.SECTIONAL_TITLE);

        return sgdataParcelsRepository.findUsingSectionalTitleSearch(sectionalSearchVm.getProvinceId(),
                sectionalSearchVm.getMunicipalityCode(),
                sectionalSearchVm.getSchemeNumber(),
                sectionalSearchVm.getSchemeName(),
                sectionalSearchVm.getFilingNumber(),
                sectionalSearchVm.getSgNumber()
                , pageable);
    }


    /**
     * @param sectionalSearchVm {@link SectionalSearchVm }
     * @return Collection<SectionalSearchErfProjection>
     */
    public Page<SectionalSearchErfProjection> getSectionalErfSearch(final SectionalSearchVm sectionalSearchVm,
                                                                    final Pageable pageable) {

        userSearchHistoryService.storeUserSearchData(sectionalSearchVm, SearchContext.SECTIONAL_TITLE);

        return sgdataParcelsRepository.findUsingSectionalErfSearch(sectionalSearchVm.getProvinceId(),
                sectionalSearchVm.getMunicipalityCode(),
                sectionalSearchVm.getTownship(),
                SearchUtils.padStringWithZero(sectionalSearchVm.getParcel(), 8),
                SearchUtils.padStringWithZero(sectionalSearchVm.getPortion(), 5),
                pageable);
    }

    /**
     * @param sectionalSearchVm {@link SectionalSearchVm }
     * @return Collection<SectionalSearchFarmProjection>
     */
    public Page<SectionalSearchFarmProjection> getSectionalFarmSearch(final SectionalSearchVm sectionalSearchVm,
                                                                      final Pageable pageable) {

        userSearchHistoryService.storeUserSearchData(sectionalSearchVm, SearchContext.SECTIONAL_TITLE);

        return sgdataParcelsRepository.findUsingSectionalFarmSearch(sectionalSearchVm.getProvinceId(),
                sectionalSearchVm.getMunicipalityCode(),
                sectionalSearchVm.getTownship(),
                sectionalSearchVm.getFarmName(),
                SearchUtils.padStringWithZero(sectionalSearchVm.getParcel(), 8),
                SearchUtils.padStringWithZero(sectionalSearchVm.getPortion(), 5),
                pageable);
    }


}
