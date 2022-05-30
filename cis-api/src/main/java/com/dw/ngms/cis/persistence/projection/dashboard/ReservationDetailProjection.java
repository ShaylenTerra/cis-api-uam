package com.dw.ngms.cis.persistence.projection.dashboard;

/**
 * @author : prateekgoel
 * @since : 15/03/21, Mon
 **/
public interface ReservationDetailProjection {

    String getStatus();

    String getMonth();

    Long getTotalRequest();
}
