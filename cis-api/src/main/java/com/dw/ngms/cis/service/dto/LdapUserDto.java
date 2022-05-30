package com.dw.ngms.cis.service.dto;

import com.dw.ngms.cis.service.dto.user.InternalUserRoleDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author : prateekgoel
 * @since : 22/11/20, Sun
 **/
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LdapUserDto {

    @NotNull
    private Long ldapUserId;

    @NotNull
    private String username;

    @NotNull
    private String password;

    @NotNull
    private String status;

    @JsonProperty("userInfo")
    @NotNull
    private UserDto userDto;

    @JsonProperty("role")
    @NotNull
    private InternalUserRoleDto internalUserRoleDto;

}
