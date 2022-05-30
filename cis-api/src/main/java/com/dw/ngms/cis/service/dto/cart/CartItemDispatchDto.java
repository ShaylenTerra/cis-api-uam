package com.dw.ngms.cis.service.dto.cart;

import lombok.Data;

/**
 * @author : prateekgoel
 * @since : 11/02/21, Thu
 **/
@Data
public class CartItemDispatchDto {

    private Long cartItemId;

    private Long cartDataId;

    private Long workflowId;

    private String lpicode;

    private String sno;

    private String item;

    private String details;

    private String timeRequired;

    private String comments;

    private String type;

    private Integer dispatchStatus;

    private String notes;
}
