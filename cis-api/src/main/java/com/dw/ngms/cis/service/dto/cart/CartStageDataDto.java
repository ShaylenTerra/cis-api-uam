package com.dw.ngms.cis.service.dto.cart;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartStageDataDto {

	private Long id;

	private Long cartId;

	@NotNull
	private Date dated;

	@NotNull
	private Long provinceId;

	@NotNull
	private Long userId;

	@NotNull
	private Long searchTypeId;

	@NotNull
	private String dataKey;

	@JsonRawValue
	private String jsonData;

	private String isProcess;

	@NotNull
	private String dataKeyValue;
}
