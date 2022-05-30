package com.dw.ngms.cis.persistence.projection.workflow;

import java.util.Date;

public interface WorkflowParcelProjection {
    Long getRecordId();
    String getReferenceNo();
    String getStatus();
    String getProcessName();
    Long getProcessId();
    Long getWorkflowId();
    Date getCompletedOn();
    Date getTriggeredOn();
}
