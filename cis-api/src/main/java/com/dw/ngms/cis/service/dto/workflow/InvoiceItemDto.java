package com.dw.ngms.cis.service.dto.workflow;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author : prateekgoel
 * @since : 26/01/21, Tue
 **/
@Data
public class InvoiceItemDto {

    @NotNull
    private String srNo;

    @NotNull
    private String item;

    @NotNull
    private String format;

    @NotNull
    private String Details;

    @NotNull
    private Double systemEstimate;

    @NotNull
    private String timeRequiredInHrs;

    @NotNull
    private String comment;

    @NotNull
    private Double finalCost;

    @NotNull
    private String lpiCode;


}
