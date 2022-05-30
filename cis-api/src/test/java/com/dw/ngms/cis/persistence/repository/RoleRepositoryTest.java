package com.dw.ngms.cis.persistence.repository;

import com.dw.ngms.cis.persistence.domains.Roles;
import com.dw.ngms.cis.testdata.RoleTestData;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author : prateekgoel
 * @since : 24/11/20, Tue
 **/
@ExtendWith(SpringExtension.class)
@DataJpaTest
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    private Roles role;

    @BeforeEach
    public void setup(){
        role =  RoleTestData.createDefaultExternalRole();
    }


    @Test
    public void whenSaveRoles_thenSaveRolesWithPrivileges(){
        Roles test_role = roleRepository.save(role);
        Assertions.assertThat(test_role).isNotNull().isSameAs(role);
    }


    @Test
    public void whenGetAllRoles_thenFetchAllRolesWithPrivileges(){
        Roles test_role = roleRepository.findByRoleName("TEST_ROLE");
        Assertions.assertThat(test_role).isNotNull();
    }


}