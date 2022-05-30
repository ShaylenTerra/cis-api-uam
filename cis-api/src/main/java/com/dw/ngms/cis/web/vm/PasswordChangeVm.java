package com.dw.ngms.cis.web.vm;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * Created by nirmal on 2020/11/12.
 */
@Data
public class PasswordChangeVm implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "email must not be empty")
	private String email;

	@NotEmpty(message = "old password must not be empty")
	private String oldPassword;

	@NotEmpty(message = "new password must not be empty")
	private String newPassword;

}
