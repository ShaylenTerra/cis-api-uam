package com.dw.ngms.cis.service.dto.cart;

import lombok.Data;

/**
 * @author : prateekgoel
 * @since : 10/02/21, Wed
 **/
@Data
public class CartItemsInvoiceDto {

    private Long cartItemId;

    private Long cartDataId;

    private Long workflowId;

    private String lpicode;

    private String sno;

    private String item;

    private String details;

    private Float finalCost;

    private Float systemEstimate;

    private String timeRequired;

    private String comments;

    private String type;

    private String notes;
}
