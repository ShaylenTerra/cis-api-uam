package com.dw.ngms.cis.service.dto.user;

import lombok.Data;

/**
 * @author : prateekgoel
 * @since : 15/12/20, Tue
 **/
@Data
public class ExternalUserRoleDto {

    private String userRoleName;

    private String userProvinceName;

    private String isActive;

    private Long userRoleId;

    private String userRoleCode;

    private String userProvinceCode;

    private String userCode;

    private String userName;

    private String externalRoleCode;
}
