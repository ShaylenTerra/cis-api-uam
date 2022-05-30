package com.dw.ngms.cis.persistence.projection.dashboard;

import java.sql.Timestamp;

/**
 * @author : prateekgoel
 * @since : 15/03/21, Mon
 **/
public interface ReservationRequestDetails {

    String getReferenceNo();

    String getUserName();

    Timestamp getDated();

    String getReservationType();

    String getStatus();
}
