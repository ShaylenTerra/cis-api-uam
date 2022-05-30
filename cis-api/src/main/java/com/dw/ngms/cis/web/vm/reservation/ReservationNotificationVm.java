package com.dw.ngms.cis.web.vm.reservation;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author prateek on 08-02-2022
 */
@Data
public class ReservationNotificationVm {

    @NotNull
    private Long workflowId;

    private Long transactionId;

    @NotNull
    private String referenceNumber;

    @NotNull
    private Long templateId;

    @NotNull
    private String context;
}
