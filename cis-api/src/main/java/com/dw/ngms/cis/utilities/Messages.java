package com.dw.ngms.cis.utilities;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * @author : prateekgoel
 * @since : 30/04/21, Fri
 **/
@Component
public class Messages {

    private final MessageSource messageSource;

    private final MessageSourceAccessor messageSourceAccessor;

    @Autowired
    public Messages(MessageSource messageSource) {
        this.messageSource = messageSource;
        this.messageSourceAccessor = new MessageSourceAccessor(this.messageSource, LocaleContextHolder.getLocale());
    }

    public String getMessage(final String msgCode) {
        return messageSourceAccessor.getMessage(msgCode);
    }

    public String getMessage(final String msgCode, Collection<Object> value) {
        return messageSourceAccessor.getMessage(msgCode, value.toArray());
    }


}
