package com.dw.ngms.cis.persistence.projection.dashboard;

import java.sql.Timestamp;

/**
 * @author : prateekgoel
 * @since : 15/03/21, Mon
 **/
public interface UserSummaryAlertDetails {

    String getReferenceNo();

    String getUserName();

    Timestamp getDated();

    String getNotificationType();

    String getStatus();

}
