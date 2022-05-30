package com.dw.ngms.cis.service;

import com.dw.ngms.cis.persistence.domains.User;
import com.dw.ngms.cis.testdata.UserTestData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author : prateekgoel
 * @since : 20/11/20, Fri
 **/
@ExtendWith(SpringExtension.class)
@SpringBootTest
class EmailServiceHandlerTest {

    @Autowired
    private EmailServiceHandler emailServiceHandler;

}