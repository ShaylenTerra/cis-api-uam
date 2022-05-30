package com.dw.ngms.cis.web.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by nirmal on 2020/11/12.
 */
@Data
public class IssueLogRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull(message = "user id must not be empty")
	private Long userId;

	@NotEmpty(message = "page must not be empty")
	private String page;

	@NotEmpty(message = "per page must not be empty")
	private String perPage;

}
