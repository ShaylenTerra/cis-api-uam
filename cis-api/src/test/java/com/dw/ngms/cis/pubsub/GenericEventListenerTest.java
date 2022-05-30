package com.dw.ngms.cis.pubsub;

import com.dw.ngms.cis.service.event.EventType;
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
public class GenericEventListenerTest {

    @Autowired
    private AppEventPublisher publisher;

    @Test
    public void testUserRegistrationApplicationEvent(){
        publisher.publishEvent("test notification",  EventType.USER_REGISTRATION);
        System.out.println("Done publishing asynchronous custom event. ");
    }

}