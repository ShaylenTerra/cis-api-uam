package com.dw.ngms.cis.persistence.projection.reservation;

/**
 * @author prateek on 16-02-2022
 */
public interface ReservationRecordIdVerificationProjection {

    String getReferenceNo();

    String getFirstName();

    String getSurName();

    String getStatus();

    String getCaption();

    Long getWorkflowId();

}
