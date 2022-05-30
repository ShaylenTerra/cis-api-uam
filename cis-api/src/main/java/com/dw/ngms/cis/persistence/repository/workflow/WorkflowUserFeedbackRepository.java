package com.dw.ngms.cis.persistence.repository.workflow;

import com.dw.ngms.cis.persistence.domains.workflow.WorkflowUserFeedback;
import com.dw.ngms.cis.persistence.projection.workflow.WorkflowUserFeedbackProjection;
import com.dw.ngms.cis.service.dto.workflow.WorkflowUserFeedbackStatusDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : pragayanshu
 * @since : 2022/03/28, Mon
 **/

@Repository
public interface WorkflowUserFeedbackRepository extends JpaRepository<WorkflowUserFeedback,Long> {

    List<WorkflowUserFeedback> findByToUserId(Long toUserId);

    @Query(value = "Select  to_char(work.TASK_STARTED,'yyyy') as year,\n" +
                   "work.TASK_INTERNALSTATUS_CAPTION as status,\n" +
                   "count(distinct work.WORKFLOWID) as noOfRequests\n" +
                   "from VW_WH_WORKFLOW_MASTER work " +
                   "inner join  VW_WH_RESERVATION_REQUEST  res on res.WORKFLOWID = work.WORKFLOWID \n" +
                   "    where work.CONFIG_PARENT_PROCESSID in(229,278) \n" +
                   "group by to_char(work.TASK_STARTED,'yyyy')  ,work.TASK_INTERNALSTATUS_CAPTION",
            nativeQuery = true)
    List<WorkflowUserFeedbackProjection> getAllFeedbackByApplicationIdAndProvince();
}
