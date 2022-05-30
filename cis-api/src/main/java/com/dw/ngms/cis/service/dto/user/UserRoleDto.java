package com.dw.ngms.cis.service.dto.user;

import lombok.Data;

@Data
public class UserRoleDto {
    private Long userRoleId;
    private Long provinceId;
    private String provinceName;
    private Long sectionItemId;
    private String sectionName;
    private Long isPrimary;
    private Long roleId;
    private String roleName;
    private Long userId;
}
