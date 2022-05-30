package com.dw.ngms.cis.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.UnsupportedEncodingException;

/**
 * @author : prateekgoel
 * @since : 20/11/20, Fri
 **/
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SmsServiceTest {

    @Autowired
    private SmsService smsService;

    @Test
    public void whenSendSms_thenIfSuccess() throws UnsupportedEncodingException {
        String response = smsService.sendSms("27783483018", "TESTCIS");
        Assertions.assertThat(response).isNotEqualTo("-5");
    }
}