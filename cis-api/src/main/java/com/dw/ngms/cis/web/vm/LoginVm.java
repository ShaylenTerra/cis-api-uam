package com.dw.ngms.cis.web.vm;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * Created by nirmal on 2020/11/12.
 */
@Data
public class LoginVm {

	@NotEmpty(message = "user name must not be empty")
	private String username;

	@NotEmpty(message = "password must not be empty")
	private String password;

}
