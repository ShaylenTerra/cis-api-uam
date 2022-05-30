package com.dw.ngms.cis.service.dto;

import com.dw.ngms.cis.service.dto.user.UserMetaDataDto;
import com.dw.ngms.cis.service.dto.user.UserRoleDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

/**
 * @author : prateekgoel
 * @since : 22/11/20, Sun
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

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

    @JsonProperty("userRoles")
    private Set<UserRoleDto> userRolesDto;

    @JsonProperty("userProfile")
    private UserMetaDataDto userMetaDataDto;

    private String transactionComment;

    private Long assistantStatusId;

    //Primary key of User_Assistant
    private Long assistantId;

    private Long resetPassword;

}
