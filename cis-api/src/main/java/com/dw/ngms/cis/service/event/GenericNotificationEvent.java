package com.dw.ngms.cis.service.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @author : prateekgoel
 * @since : 20/11/20, Fri
 **/
@Getter
public class GenericNotificationEvent<T> extends ApplicationEvent {

    private  T content;
    private EventType eventType;

    public GenericNotificationEvent(Object source) {
        super(source);
    }

    public GenericNotificationEvent(Object source, T content, EventType eventType) {
        super(source);
        this.content = content;
        this.eventType = eventType;
    }
}
