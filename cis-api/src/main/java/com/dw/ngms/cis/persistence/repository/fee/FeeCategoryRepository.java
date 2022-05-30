package com.dw.ngms.cis.persistence.repository.fee;

import com.dw.ngms.cis.persistence.domains.fee.FeeCategory;
import com.dw.ngms.cis.persistence.projection.ngi.NgiSearchDataProjection;
import com.dw.ngms.cis.service.dto.simulator.LodegementTypeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * @author : prateekgoel
 * @since : 27/11/20, Fri
 **/
@Repository
public interface FeeCategoryRepository extends JpaRepository<FeeCategory, Long> {

    @Query(value = "Select fcSub.SUBCATID    as   recordId,\n" +
            "       FEE_CATEGORY.NAME as categoryName,\n" +
            "       fcSub.NAME        as name,\n" +
            "       fcSub.Description as description,\n" +
            "       'NGI Data'        as documentType,\n" +
            "       'NGI Data'        as documentSubType,\n" +
            "       9                 as documentTypeId,\n" +
            "       9                 as documentSubTypeId\n" +
            "from FEE_CATEGORY\n" +
            "         inner join fee_subcategory fcSub on CATID = fcSub.CATEGORYID\n" +
            "where fcSub.ISACTIVE = 1 \n" +
            "  and FEE_CATEGORY.CATID = :categoryId \n" +
            "order by fcSub.NAME", nativeQuery = true)
    Collection<NgiSearchDataProjection> getNgiSearchDataBasedOnCategoryId(final Long categoryId);


    @Query("select  feeCategoryId as categoryId ,name as name" +
            " from FeeCategory where isActive =1 and CATID in (174,175,176,2,3)")
    List<LodegementTypeDto> getAllLodegementType();
}
