package com.dw.ngms.cis.persistence.repository;

import com.dw.ngms.cis.persistence.domain.SearchType;
import com.dw.ngms.cis.persistence.projection.ngi.NgiSearchDataProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface NgiDataRepository extends JpaRepository<SearchType, Long> {

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
            "order by fcSub.NAME",
            nativeQuery = true,
            countQuery = "Select count(*)" +
                    "from FEE_CATEGORY\n" +
                    "         inner join fee_subcategory fcSub on CATID = fcSub.CATEGORYID\n" +
                    "where fcSub.ISACTIVE = 1 \n" +
                    "  and FEE_CATEGORY.CATID = :categoryId \n" +
                    "order by fcSub.NAME")
    Page<NgiSearchDataProjection> getNgiSearchDataBasedOnCategoryId(final Long categoryId,
                                                                    final Pageable pageable);

}
