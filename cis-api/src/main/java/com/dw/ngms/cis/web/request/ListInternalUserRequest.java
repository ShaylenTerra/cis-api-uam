package com.dw.ngms.cis.web.request;

import com.dw.ngms.cis.enums.UserType;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * Created by nirmal on 2020/11/12.
 */
@Data
public class ListInternalUserRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "user type must not be empty")
	private UserType userType;

	@NotEmpty(message = "page must not be empty")
	private String page;

	@NotEmpty(message = "per page must not be empty")
	private String perPage;

}
