package com.dw.ngms.cis.persistence.projection.reservation;

import java.time.LocalDateTime;

/**
 * @author : pragayanshu
 * @since : 2022/02/19, Sat
 **/
public interface ReservationTransferListProjection {

    Long getOutcomeId();

    String getDesignation();

    String getLpi();

    String getStatus();

    String getReason();

    LocalDateTime getIssueDate();

    LocalDateTime getExpiryDate();

    Long getExpiryInDays();

    String getReferenceNumber();

    String getName();

    String getProvinceName();

    String getFirstName();

    String getSurName();

    Long getWorkflowId();

    Long getTransferId();


}
