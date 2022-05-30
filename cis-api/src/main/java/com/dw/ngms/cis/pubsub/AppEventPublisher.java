package com.dw.ngms.cis.pubsub;

import com.dw.ngms.cis.service.event.EventType;
import com.dw.ngms.cis.service.event.GenericNotificationEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @author : prateekgoel
 * @since : 20/11/20, Fri
 **/
@Component
@Slf4j
public class AppEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;


    public AppEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishEvent(final Object value, final EventType eventType) {
        log.debug("An application event of type {} occur !!", eventType.name());
        GenericNotificationEvent<Object> event = new GenericNotificationEvent<>(this, value, eventType);
        applicationEventPublisher.publishEvent(event);
        log.debug(" event published successfully !! ");
    }


}
