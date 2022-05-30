package com.dw.ngms.cis.service;

import com.dw.ngms.cis.enums.SearchContext;
import com.dw.ngms.cis.persistence.projection.ngi.NgiSearchDataProjection;
import com.dw.ngms.cis.persistence.repository.NgiDataRepository;
import com.dw.ngms.cis.vms.SearchVm;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class NgiSearchService {

    private final NgiDataRepository ngiDataRepository;

    private final UserSearchHistoryService userSearchHistoryService;

    /**
     * @param searchVm {@link SearchVm}
     * @return Collection<NgiSearchDataProjection>
     */
    public Page<NgiSearchDataProjection> searchNgiData(final SearchVm searchVm, final Pageable pageable) {
        //userSearchHistoryService.storeUserSearchData(searchVm, SearchContext.NGI);
        return ngiDataRepository.getNgiSearchDataBasedOnCategoryId(searchVm.getSearchFilterId(),pageable);
    }

}
