package com.dw.ngms.cis.persistence.repository.workflow;

import com.dw.ngms.cis.persistence.domains.workflow.WorkflowProcessMatrix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author : prateekgoel
 * @since : 29/12/20, Tue
 **/
@Repository
public interface WorkflowProcessMatrixRepository extends JpaRepository<WorkflowProcessMatrix, Long> {

    Collection<WorkflowProcessMatrix> findByTagAndActive(final String tag, final Long active);


}
