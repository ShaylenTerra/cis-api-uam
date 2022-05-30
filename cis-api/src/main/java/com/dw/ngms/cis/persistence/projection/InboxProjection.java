package com.dw.ngms.cis.persistence.projection;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author : prateekgoel
 * @since : 22/12/20, Tue
 **/
public interface InboxProjection {


    Long getProcessId();

    String getProcessName();

    String getActionRequired();

    String getActionRequiredDescription();

    Long getWorkflowId();

    String getReferenceNo();

    Long getProvinceId();

    Long getTurnaroundDuration();

    Long getActionId();

    Date getAllocatedOn();

    String getAssignedToUser();

    Date getDiarisedate();

    Long getInternalStatus();

    String getInternalStatusCaption();

    Long getExternalStatus();

    String getExternalStatusCaption();

    Long getPriorityFlag();

    String getPriorityName();

    Long getEscalationTime();

    String getActionContext();

    LocalDateTime getTriggeredOn();

    Long getNodeId();

    Long getUserId();

}

