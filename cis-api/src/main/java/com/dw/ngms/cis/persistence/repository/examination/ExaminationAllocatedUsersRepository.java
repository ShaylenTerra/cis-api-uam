package com.dw.ngms.cis.persistence.repository.examination;

import com.dw.ngms.cis.persistence.domains.examination.ExaminationAllocatedUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExaminationAllocatedUsersRepository extends JpaRepository<ExaminationAllocatedUsers, Long> {

    @Query("SELECT E FROM ExaminationAllocatedUsers E WHERE E.examId=?1")
    ExaminationAllocatedUsers findAllocatedUserByExaminationId(Long examinationId);

    @Query("SELECT E FROM ExaminationAllocatedUsers E WHERE E.examAllocatedId=?1")
    ExaminationAllocatedUsers findAllocatedUserByExamAllocatedId(Long examinationId);
}
