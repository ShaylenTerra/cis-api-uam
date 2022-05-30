package com.dw.ngms.cis.testdata;

import com.dw.ngms.cis.service.dto.LdapUserDto;
import com.dw.ngms.cis.service.dto.UserDto;
import com.dw.ngms.cis.service.dto.user.InternalUserRoleDto;

import java.util.Date;

/**
 * @author : prateekgoel
 * @since : 28/11/20, Sat
 **/
public class LdapUserTestData {

    public static LdapUserDto createLdapUserDto(){
        InternalUserRoleDto internalUserRoleDto = InternalUserRoleDto.builder()
                .internalRoleCode("TESTROLE")
                .isActive("1")
                .signedAccessDocPath("/signedAccessDocPath")
                .userCode("TESTUSERCODE")
                .userName("prateek.abc")
                .userProvinceCode("TEST-PROVINCECODE")
                .userProvinceName("TEST-PROVINCENAME")
                .userRoleCode("TEST-ROLECODE")
                .userRoleName("TEST-ROLENAME")
                .userSectionCode("TEST-USERSECTION")
                .userSectionName("TEST-USERSECTIONNAME").build();

        UserDto userDto = UserDto.builder()
                .countryCode("TEST-CONTRYCODE")
                .createdDate(new Date())
                .email("prateek@harvesting.co")
                .firstName("TEST-FIRSTNAME")
                .mobileNo("0000000000")
                .surname("TEST_SURNAME")
                .telephoneNo("0000000000")
                .userCode("TEST-USERCODE")
                .userName("prateek.abc")
                .build();

        return LdapUserDto.builder()
                .username("phila")
                .password("test123")
                .internalUserRoleDto(internalUserRoleDto)
                .userDto(userDto)
                .build();

    }
}
