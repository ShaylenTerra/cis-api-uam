package com.dw.ngms.cis.service.prepackage;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author : prateekgoel
 * @since : 17/03/21, Wed
 **/
@ActiveProfiles("sit")
@SpringBootTest
public class PrePackageServiceTest {

    @Autowired
    private PrePackageService prePackageService;

    @Test
    public void execute_subscription() {
        prePackageService.executeSubscriptionById(427L);
    }

}