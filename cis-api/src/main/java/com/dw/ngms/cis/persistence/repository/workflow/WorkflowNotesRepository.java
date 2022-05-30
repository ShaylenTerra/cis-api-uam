package com.dw.ngms.cis.persistence.repository.workflow;

import com.dw.ngms.cis.persistence.domains.workflow.WorkflowNotes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : prateekgoel
 * @since : 29/12/20, Tue
 **/
@Repository
public interface WorkflowNotesRepository extends JpaRepository<WorkflowNotes, Long> {
}
