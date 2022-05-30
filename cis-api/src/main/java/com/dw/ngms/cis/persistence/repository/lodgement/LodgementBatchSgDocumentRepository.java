package com.dw.ngms.cis.persistence.repository.lodgement;

import com.dw.ngms.cis.persistence.domains.lodgement.LodgementBatchSgDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @author prateek on 21-04-2022
 */
@Repository
public interface LodgementBatchSgDocumentRepository
        extends JpaRepository<LodgementBatchSgDocument,Long> {

    @Query("SELECT max(LBSD.docNumber) FROM LodgementBatchSgDocument LBSD where " +
            " LBSD.provinceId = :provinceId and LBSD.docTypeItemId = :documentTypeItemId")
    Long getMaxDocumentNumber(final Long provinceId, final Long documentTypeItemId);

}
