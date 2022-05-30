package com.dw.ngms.cis.web.vm.cart;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckoutVm {

	private Long userId;

	private Long workflowId;

	private Long templateId;

	private Long transactionId;

	private Long cartId;

}
