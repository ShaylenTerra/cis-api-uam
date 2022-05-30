package com.dw.ngms.cis.persistence.repository.workflow;

import com.dw.ngms.cis.persistence.domains.workflow.Workflowprocess;
import com.dw.ngms.cis.persistence.projection.workflow.WorkflowProcessProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author : prateekgoel
 * @since : 22/02/21, Mon
 **/
@Repository
public interface WorkflowProcessRepository extends JpaRepository<Workflowprocess, Long> {


    @Query("SELECT WP.processid as processId, WP.name as processName FROM Workflowprocess WP " +
            "WHERE WP.isactive = :isActive and WP.provinceid =:provinceId")
    Collection<WorkflowProcessProjection> getAllProcess(final Long isActive, final Long provinceId);

}

