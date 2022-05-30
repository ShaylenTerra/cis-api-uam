package com.dw.ngms.cis.persistence.repository.lodgement;

import com.dw.ngms.cis.persistence.domains.lodgement.LodgementBatch;
import com.dw.ngms.cis.persistence.domains.lodgement.LodgementDraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author prateek on 21-04-2022
 */
@Repository
public interface LodgementBatchRepository
        extends JpaRepository<LodgementBatch,Long> {

    LodgementBatch findByDraftId(final Long draftId);

    LodgementBatch findByBatchId(final Long batchId);

    @Query("Select MAX(LB.batchNumber) from LodgementBatch LB where LB.provinceId =:provinceId")
    Long getMaxBatch(final Long provinceId);


}
