package com.dw.ngms.cis.persistence.repository.workflow;

import com.dw.ngms.cis.persistence.domains.workflow.WorkflowActionProductivityView;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : prateekgoel
 * @since : 24/02/21, Wed
 **/
@Repository
public interface WorkflowActionProductivityRepository extends JpaRepository<WorkflowActionProductivityView, Long> {

    Page<WorkflowActionProductivityView> findAllByWorkflowId(final Long workflowId, final Pageable pageable);

}
