package com.dw.ngms.cis.cisworkflow.persistence.repository;

import com.dw.ngms.cis.cisworkflow.persistence.domain.Workflow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : prateekgoel
 * @since : 20/05/21, Thu
 **/
@Repository
public interface WorkflowRepository extends JpaRepository<Workflow, Long> {

    @Query("SELECT t.workflowId FROM Workflow t")
    List<Object[]> getWorkflowID();

    @Query("SELECT t.workflowId FROM Workflow t where t.processId=:processId")
    List<Object[]> getWorkflowProcessCount(@Param("processId") long paramLong);

    Workflow findByWorkflowId(final Long workflowId);

}
