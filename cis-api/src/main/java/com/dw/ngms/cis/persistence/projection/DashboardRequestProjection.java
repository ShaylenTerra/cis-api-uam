package com.dw.ngms.cis.persistence.projection;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author : prateekgoel
 * @since : 23/12/20, Wed
 **/
public interface DashboardRequestProjection {

    Long getProcessId();

    String getProcessName();



    Long getWorkflowId();

    String getReferenceNumber();

    Long getTurnaroundDuration();

    String getAssignedToUser();

    Long getInternalStatus();

    String getInternalStatusCaption();

    Long getExternalStatus();

    String getExternalStatusCaption();

    Long getPriorityFlag();

    String getPriorityName();

    Long getUserId();

    LocalDateTime getLastStatusUpdate();

    LocalDateTime getTriggeredOn();

    Long getActionId();

    Long getNodeId();

    Long getActionRequired();

    String getActionRequiredCaption();



}
