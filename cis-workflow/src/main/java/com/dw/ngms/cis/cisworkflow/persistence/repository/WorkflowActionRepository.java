package com.dw.ngms.cis.cisworkflow.persistence.repository;

import com.dw.ngms.cis.cisworkflow.persistence.domain.WorkflowAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : prateekgoel
 * @since : 20/05/21, Thu
 **/
@Repository
public interface WorkflowActionRepository extends JpaRepository<WorkflowAction, Long> {

    @Query("SELECT t.actionId FROM WorkflowAction t")
    List<Long> getActionId();

    WorkflowAction findByActionId(final Long actionId);

}
