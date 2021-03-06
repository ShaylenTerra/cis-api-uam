package com.dw.ngms.cis.persistence.repository.examination;

import com.dw.ngms.cis.persistence.domains.examination.Examination;
import com.dw.ngms.cis.service.dto.examination.ExaminationDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


/**
 * @author Nontokozo on 30-05-2022
 */

@Repository
public interface ExaminationRepository extends JpaRepository<Examination, Long> {

//   Examination findExamById(final Long examId);

   @Query("SELECT COUNT(name) FROM Examination WHERE name=?1")
   int CheckExam(String name);

   @Query("SELECT E FROM Examination E WHERE E.workflowId=?1")
   Examination getExaminationByWorkflowId(Long workflowId);

}
