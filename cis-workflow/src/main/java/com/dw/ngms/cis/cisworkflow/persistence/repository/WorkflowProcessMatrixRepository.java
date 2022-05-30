package com.dw.ngms.cis.cisworkflow.persistence.repository;

import com.dw.ngms.cis.cisworkflow.persistence.domain.WorkflowProcessMatrix;
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
public interface WorkflowProcessMatrixRepository extends JpaRepository<WorkflowProcessMatrix, Long> {

    List<WorkflowProcessMatrix> findByIsActive(@Param("active") Long isActive);

    WorkflowProcessMatrix findByMatrixId(final Long paramInt);

    @Query("SELECT max(t.id)+1 FROM WorkflowProcessMatrix t")
    Long getNextId();

}
