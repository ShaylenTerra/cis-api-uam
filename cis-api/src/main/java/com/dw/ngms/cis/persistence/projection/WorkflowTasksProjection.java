package com.dw.ngms.cis.persistence.projection;

import java.util.Date;

/**
 * @author : prateekgoel
 * @since : 23/12/20, Wed
 **/
public interface WorkflowTasksProjection {

    Long getActionId();

    Long getTransactionId();

    Long getUserId();

    Date getPostedOn();

    Long getNoteId();

    Long getActionRequired();

    Date getActedOn();

    Long getActionTakenId();

    Long getActionTransactionId();

    String getActionTakenBy();

    String getActionReq();

    String getActionTaken();

    String getNote();
}
