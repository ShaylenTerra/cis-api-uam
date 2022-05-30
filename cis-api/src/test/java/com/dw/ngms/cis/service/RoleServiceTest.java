package com.dw.ngms.cis.service;

import com.dw.ngms.cis.persistence.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author : prateekgoel
 * @since : 24/11/20, Tue
 **/
@SpringBootTest
public class RoleServiceTest {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleService roleService;


}