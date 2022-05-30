package com.dw.ngms.cis.testdata;

import com.dw.ngms.cis.persistence.domains.Roles;

/**
 * @author : prateekgoel
 * @since : 24/11/20, Tue
 **/
public class RoleTestData {

    public static Roles createDefaultExternalRole() {
        return Roles.builder()
                .roleName("TEST_ROLE")
                .roleCode("TEST001")
                .description("test role for junit testing")
                .build();
    }


}
