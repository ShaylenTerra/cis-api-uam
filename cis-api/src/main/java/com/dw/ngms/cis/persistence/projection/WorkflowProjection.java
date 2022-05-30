package com.dw.ngms.cis.persistence.projection;

import java.util.Date;

/**
 * @author : prateekgoel
 * @since : 23/12/20, Wed
 **/
public interface WorkflowProjection {

    Long getProcessId();

    String getProcessName();

    Long getWorkflowId();

    String getReferenceNo();

    String getTurnaroundDuration();

    String getInitiatedUser();

    Long getInternalStatus();

    String getInternalStatusCaption();

    Long getExternalStatus();

    String getExternalStatusCaption();

    Long getPriorityFlag();

    String getPriorityName();

    Date getTriggeredOn();
}
