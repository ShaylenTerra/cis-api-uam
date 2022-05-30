package com.dw.ngms.cis.web.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by nirmal on 2020/11/21.
 */
@Data
public class UpdateExternalUserProfileRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull(message = "user id must not be null")
	private Long userId;

	private String titleCode;
	private String title;
	private String firstName;
	private String lastName;

	private String organization;
	private String organizationCode;

	private String sector;
	private String sectorCode;

	private String addrLine1;
	private String addrLine2;
	private String addrLine3;

	private Long mobileNo;

	private String postalCode;

	private String modeOfComm;
	private String modeOfCommCode;

	private String subscribeNews;
	private String subscribeEvents;
	private String subscribeNotifications;
}
