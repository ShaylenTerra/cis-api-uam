package com.dw.ngms.cis.service.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : prateekgoel
 * @since : 28/11/20, Sat
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InternalUserRoleDto {

    private Long userRoleId;

    private String userRoleCode;

    private String userRoleName;

    private String userSectionCode;

    private String userSectionName;

    private String userProvinceCode;

    private String userProvinceName;

    private String userCode;

    private String userName;

    private String signedAccessDocPath;

    private String isActive;

    private String internalRoleCode;

}
