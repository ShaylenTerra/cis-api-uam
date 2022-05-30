package com.dw.ngms.cis.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by nirmal on 2020/11/18.
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateResponse {

	private Boolean created;

}
