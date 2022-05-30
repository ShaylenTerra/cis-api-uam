package com.dw.ngms.cis.cisworkflow.persistence.repository;

import com.dw.ngms.cis.cisworkflow.persistence.domain.MOfficeTimings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : prateekgoel
 * @since : 20/05/21, Thu
 **/
@Repository
public interface OfficeTimingRepository extends JpaRepository<MOfficeTimings, Long> {
    @Query("SELECT t.timingId FROM MOfficeTimings t")
    List<Object[]> getTimingId();

    @Query("SELECT t FROM MOfficeTimings t where t.provinceId=:provinceId")
    List<MOfficeTimings> findByProvinceId(@Param("provinceId") Long provinceId);

    @Query("SELECT t FROM MOfficeTimings t WHERE t.timingId in (SELECT max(t1.timingId) " +
            " from MOfficeTimings t1 group by t1.provinceId)")
    List<MOfficeTimings> findUsingLastofAll();
}
