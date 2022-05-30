package com.dw.ngms.cis.service;

import com.dw.ngms.cis.persistence.projection.ngi.NgiSearchDataProjection;
import com.dw.ngms.cis.persistence.repository.fee.FeeCategoryRepository;
import com.dw.ngms.cis.persistence.repository.listmanagement.ListMasterRepository;
import com.dw.ngms.cis.service.dto.listmanagement.ListMasterDto;
import com.dw.ngms.cis.service.mapper.ListMasterMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @author : prateekgoel
 * @since : 06/12/20, Sun
 **/
@Service
@Slf4j
@AllArgsConstructor
public class ListMasterService {

    private final ListMasterRepository listMasterRepository;

    private final ListMasterMapper listMasterMapper;

    private final FeeCategoryRepository feeCategoryRepository;

    /**
     * method to return all ManagementListMaster data
     *
     * @return collection of ManagementListMasterDto
     */
    public Page<ListMasterDto> getAllManagementListMaster(final Pageable pageable) {
        return listMasterRepository.findAll(pageable)
                .map(listMasterMapper::managementListMasterToManagementListMasterDto);
    }

    /**
     *
     * @param categoryId categoryId
     * @return Collection<NgiSearchDataProjection>
     */
    public Collection<NgiSearchDataProjection> getNgiSearchData(final Long categoryId) {
        return feeCategoryRepository.getNgiSearchDataBasedOnCategoryId(categoryId);
    }
}
