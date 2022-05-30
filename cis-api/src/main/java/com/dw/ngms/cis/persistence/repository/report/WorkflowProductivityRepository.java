package com.dw.ngms.cis.persistence.repository.report;

import com.dw.ngms.cis.persistence.domains.report.WorkflowProductivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : prateekgoel
 * @since : 12/02/21, Fri
 **/
@Repository
public interface WorkflowProductivityRepository extends JpaRepository<WorkflowProductivity, Long> {

    int deleteAllByActionId(final Long actionId);

}
