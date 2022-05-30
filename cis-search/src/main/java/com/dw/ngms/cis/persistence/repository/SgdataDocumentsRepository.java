package com.dw.ngms.cis.persistence.repository;

import com.dw.ngms.cis.persistence.domain.SgdataDocuments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : prateekgoel
 * @since : 06/05/21, Thu
 **/
@Repository
public interface SgdataDocumentsRepository extends JpaRepository<SgdataDocuments, Long>, CustomSgdataDocumentRepository {

}
