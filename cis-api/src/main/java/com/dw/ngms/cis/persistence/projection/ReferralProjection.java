package com.dw.ngms.cis.persistence.projection;

import java.util.Date;

/**
 * @author : prateekgoel
 * @since : 29/12/20, Tue
 **/
public interface ReferralProjection {

    Long getProcessId();

    String getProcessName();

    Long getWorkflowId();

    String getReferenceNo();

    Date getTriggeredOn();

    String getFromUser();

    String getRequestNote();

    String getToUser();

    Date getCreatedOn();

    String getReferralInput();

    String getStatus();

}
