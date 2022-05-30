package com.dw.ngms.cis.web.vm;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * Created by nirmal on 2020/11/12.
 */
@Data
public class ChangeEmailVm implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "user id must not be empty")
	private String userId;

	@NotEmpty(message = "old email must not be empty")
	private String oldEmail;

	@NotEmpty(message = "new email must not be empty")
	private String newEmail;

}
