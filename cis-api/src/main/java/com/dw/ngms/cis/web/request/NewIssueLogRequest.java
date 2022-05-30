package com.dw.ngms.cis.web.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by nirmal on 2020/11/18.
 */
@Data
public class NewIssueLogRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull(message = "user id must not be empty")
	private long userId;

	@NotNull(message = "issue type must not be empty")
	private long issueTypeItemId;

	@NotEmpty(message = "description must not be empty")
	private String description;

}
