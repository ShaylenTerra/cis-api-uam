package com.dw.ngms.cis.service;

import com.dw.ngms.cis.enums.UserType;
import com.dw.ngms.cis.persistence.domains.User;
import com.dw.ngms.cis.pubsub.AppEventPublisher;
import com.dw.ngms.cis.service.event.EventType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author : prateekgoel
 * @since : 24/11/20, Tue
 **/
@SpringBootTest
public class LoginAuditorServiceTest {

    @Autowired
    private LoginAuditorService loginAuditorService;

    @Autowired
    private AppEventPublisher appEventPublisher;

    @Test
    public void whenPublishingLoggedInEvent_thenEventShouldPersist(){
        User user = new User();
        user.setUserName("prateek");
        user.setUserTypeItemId(UserType.EXTERNAL);
        appEventPublisher.publishEvent(user,EventType.USER_LOGGED_IN_SUCCESSFULLY);
    }

}