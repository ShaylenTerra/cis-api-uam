package com.dw.ngms.cis.service.dto.cart;

import com.dw.ngms.cis.persistence.domains.cart.CartStageData;
import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartStageDto {

	private Long cartId;

	private Long provinceId;

	private Long userId;

	private Date dated;

	@JsonRawValue
	private String requesterInformation;

	private Long workflowId;

	private Set<CartStageData> cartStageData;
}
