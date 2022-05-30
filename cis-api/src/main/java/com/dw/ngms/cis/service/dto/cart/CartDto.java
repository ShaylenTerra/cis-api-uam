package com.dw.ngms.cis.service.dto.cart;

import com.dw.ngms.cis.persistence.domains.cart.CartData;
import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {

	private Long cartId;

	private Long provinceId;

	private Long userId;

	private Date dated;

	@JsonRawValue
	private String addressData;

	@JsonRawValue
	private String requestorData;

	private Long workflowId;

	@Size(min = 1)
	private Set<CartData> cartData;
}
