package com.dw.ngms.cis.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * @author : prateekgoel
 * @since : 20/03/21, Sat
 **/
@ActiveProfiles("sit")
@SpringBootTest
public class DataViewerServiceTest {

    @Autowired
    private DataViewerService dataViewerService;

    @Test
    public void executeInProgressDataRequest() {
        dataViewerService.executeAllInProgressDataViewerRequest();
    }

}