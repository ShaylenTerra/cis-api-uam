package com.dw.ngms.cis.web.vm.cart;

import lombok.Data;

/**
 * @author : prateekgoel
 * @since : 11/02/21, Thu
 **/
@Data
public class CartItemDispatchVm {

    private Long cartItemId;

    private String comments;

    private Integer dispatchStatus;

}
