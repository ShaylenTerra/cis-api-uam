package com.dw.ngms.cis.persistence.repository.workflow;

import com.dw.ngms.cis.persistence.domains.workflow.AutoDispatchWorkflow;
import com.dw.ngms.cis.persistence.domains.workflow.WorkflowAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface WorkflowActionRepository extends JpaRepository<WorkflowAction, Long> {

    Collection<WorkflowAction> findByWorkflowId(final Long workflowId);

    WorkflowAction findByActionId(final Long actionId);

    @Query(nativeQuery = true, name = "findAllAutoDispatch")
    Collection<AutoDispatchWorkflow> getAutoDispatchProcess(final Long workflowId);

    @Query("select distinct(userId) from WorkflowAction where workflowId =:workflowId")
    Collection<Long> getAllDistinctUserIdForWorkflowId(final Long workflowId);

}
