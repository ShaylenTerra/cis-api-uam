package com.dw.ngms.cis.service.report;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.Duration;
import java.time.LocalTime;

/**
 * @author : prateekgoel
 * @since : 12/02/21, Fri
 **/
@ActiveProfiles("sit")
@SpringBootTest
public class ProductivityReportGeneratorTest {

    @Autowired
    public ProductivityReportGenerator productivityReportGenerator;

    @Test
    void generateWorkflowProductivity() {
        LocalTime localTime1 = LocalTime.of(10, 36);
        LocalTime localTime2 = LocalTime.of(10, 37);
        System.out.println(Duration.between(localTime2, localTime1).toMinutes());

        //productivityReportGenerator.generateWorkflowProductivity(10055L);
    }
}