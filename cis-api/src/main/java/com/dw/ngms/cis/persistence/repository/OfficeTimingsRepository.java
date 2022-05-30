package com.dw.ngms.cis.persistence.repository;

import com.dw.ngms.cis.enums.OfficeTimingType;
import com.dw.ngms.cis.persistence.domains.OfficeTimings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;

/**
 * Created by nirmal on 2020/11/18.
 */
@Repository
public interface OfficeTimingsRepository extends JpaRepository<OfficeTimings, Long> {

    Page<OfficeTimings> findByProvinceIdAndIsActiveAndOfficeTimingType(final Long provinceId,
                                                                       final Long isActive,
                                                                       final OfficeTimingType officeTimingType,
                                                                       final Pageable pageable);

    Collection<OfficeTimings> findByUserIdAndIsActiveAndOfficeTimingType(final Long userId,
                                                                         final Long isActive,
                                                                         OfficeTimingType officeTimingType);

    @Modifying
    @Query(" update OfficeTimings ot set ot.isActive =:status where ot.officeTimeId =:officeTimeId")
    int updateOfficeTimingsStatus(final Long status, final Long officeTimeId);

    @Query("SELECT ot FROM OfficeTimings ot where ot.provinceId = :provinceId and ot.isActive = 1 " +
            "and ot.fromDate <= :date and ot.toDate >= :date and ot.officeTimingType = :officeTimingType")
    OfficeTimings findUsingProvinceIdAndDateAndOfficeTimingType(final Long provinceId,
                                                                final LocalDate date,
                                                                OfficeTimingType officeTimingType);


}
