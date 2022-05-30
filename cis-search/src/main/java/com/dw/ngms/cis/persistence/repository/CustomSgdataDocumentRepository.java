package com.dw.ngms.cis.persistence.repository;

import com.dw.ngms.cis.persistence.domain.SgdataDocuments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

/**
 * @author : prateekgoel
 * @since : 19/05/21, Wed
 **/
public interface CustomSgdataDocumentRepository {

    Page<SgdataDocuments> findByRecordId(final Long recordId, final Pageable pageable);

    Collection<SgdataDocuments> findByRecordId(final Long recordId);

}
