package com.dw.ngms.cis.persistence.projection.lodgment;

import java.time.LocalDateTime;

/**
 * @author prateek on 10-01-2022
 */
public interface LodgmentDraftListingProjection {

    Long getProcessId();

    Long getDraftId();

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

    LocalDateTime getTriggeredOn();

    Long getUserId();

    LocalDateTime getLastStatusUpdate();

    Long getActionId();

    Long getNodeId();

    Long getActionRequired();

    String getActionRequiredCaption();

    String getReservationName();

    String getProvinceName();

}
