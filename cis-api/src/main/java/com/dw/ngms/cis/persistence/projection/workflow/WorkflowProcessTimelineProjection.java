package com.dw.ngms.cis.persistence.projection.workflow;

import org.apache.commons.net.ntp.TimeStamp;

import java.time.LocalDateTime;

/**
 * @author prateek on 09-05-2022
 */
public interface WorkflowProcessTimelineProjection {

    LocalDateTime getTriggeredOn();
    LocalDateTime getCompletedOn();
    Long getWorkflowId();
    Long getProcessId();
    String getProcessName();
    String getStatus();
    String getReferenceNo();
    Long getRecordId();
    String getSummary();

}
