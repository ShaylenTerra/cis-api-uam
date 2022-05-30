package com.dw.ngms.cis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchUserLogDto {

	private Long userId;

	private Long provinceId;

	private Long searchTypeId;

	private Long searchFilterId;

	private String caption;

	private String data;

}
