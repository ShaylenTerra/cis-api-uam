package com.dw.ngms.cis.cisemailer.util;

import com.dw.ngms.cis.cisemailer.service.Mail;
import lombok.AllArgsConstructor;
import org.apache.commons.text.StringSubstitutor;

import java.util.Map;

/**
 * @author : prateekgoel
 * @since : 29/05/21, Sat
 **/
public final class MailUtils {


    public static String substituteStringToken(final String template, final Map<String, String> placeholder) {
        if (null == template)
            return null;
        return StringSubstitutor.replace(template, placeholder);
    }

}
