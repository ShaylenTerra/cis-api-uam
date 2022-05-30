package com.dw.ngms.cis.web.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by nirmal on 2020/11/21.
 */
@Data
public class AddNewRoleRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull(message = "user id must not be null")
	private Long userId;

	private String roleCode;
	private String roleName;

	private String provinceCode;
	private String provinceName;
	
	private String orgCode;
	private String orgName;
	
	private String userCode;
	private String userName;
}
