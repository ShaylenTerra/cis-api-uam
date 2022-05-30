package com.dw.ngms.cis.service.dto.user;

import lombok.Data;

import java.util.Date;

/**
 * @author : prateekgoel
 * @since : 07/05/21, Fri
 **/
@Data
public class UserBasicInfoDto {

    private Long userId;

    private String userCode;

    private String userName;

    private String userType;

    private String firstName;

    private String surname;

    private String mobileNo;

    private String telephoneNo;

    private String email;

    private Long statusId;

    private String status;

    private Date createdDate;

    private String countryCode;

    private Long titleItemId;

    private String title;

    private Long provinceId;

    private String province;

}
