package com.dw.ngms.cis.service.dto.user;

import com.dw.ngms.cis.enums.UserType;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author : prateekgoel
 * @since : 26/02/21, Fri
 **/
@Data
public class RolesDto {

    private Long roleId;

    @NotNull
    private String roleName;

    @NotNull
    private UserType userTypeItemId;

    private String description;

    @NotNull
    private Long isActive;

    private String roleCode;

    private Long parentRoleId;

    private Long approvalRequired;

    private Long sectionItemId;

}
