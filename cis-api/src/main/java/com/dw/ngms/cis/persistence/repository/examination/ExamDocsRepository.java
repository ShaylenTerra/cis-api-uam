package com.dw.ngms.cis.persistence.repository.examination;

import com.dw.ngms.cis.persistence.domains.examination.Examination;
import com.dw.ngms.cis.persistence.domains.examination.Examination_Documents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamDocsRepository extends JpaRepository<Examination_Documents, Long> {

}
