package com.dw.ngms.cis.persistence.repository.prepackage;

import com.dw.ngms.cis.persistence.domains.prepackage.Location;
import com.dw.ngms.cis.persistence.projection.prepackage.PrepackageMajorRegionOrAdminDistrict;
import com.dw.ngms.cis.persistence.projection.prepackage.PrepackageMinorRegion;
import com.dw.ngms.cis.persistence.projection.prepackage.PrepackageMunicipality;
import com.dw.ngms.cis.persistence.projection.prepackage.PrepackageProvince;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * @author : prateekgoel
 * @since : 23/03/21, Tue
 **/
@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {


    @Query("Select loc.boundaryId as provinceId, loc.caption as province, loc.mdbcode as mdbCode  " +
            "from Location loc where loc.category in('P')")
    Collection<PrepackageProvince> getProvince();

    @Query(value = "Select CAPTION as municipality , MDBCODE as mdbCode from VW_MUNCIPALITY " +
            "where PROVINCEID= :provinceId  order by CAPTION",
            nativeQuery = true)
    Collection<PrepackageMunicipality> getMunicipality(final Long provinceId);

    @Query("Select loc.caption as majorRegionOrAdminDistrict, loc.mdbcode as mdbCode, loc.boundaryId as boundaryId from Location loc\n" +
            "            where loc.category in ('T','AR') and loc.boundaryId in (Select distinct registrationTownshipId \n" +
            "            from SgdataParcels ) and loc.parentBoundaryId = :provinceId")
    Page<PrepackageMajorRegionOrAdminDistrict> getMajorRegion(final Long provinceId, Pageable pageable);

    @Query("Select loc.caption as minorRegion ,loc.mdbcode as mdbCode from Location loc " +
            "where loc.category in('T')   and loc.parentBoundaryId = :provinceId")
    Page<PrepackageMinorRegion> getMinorRegion(final Long provinceId, Pageable pageable);

    @Query("select loc from Location loc where boundaryId = :boundryId ")
    Location findLocationByBoundaryId(final Long boundryId)
            ;
    Page<Location> findByCategoryIn(final List<String> categories,Pageable pageable);

    Page<Location> findByCategoryInAndReservationSystemNotNullAndParentBoundaryId(final List<String> categories,Long parentBoundaryId,Pageable pageable);
}
