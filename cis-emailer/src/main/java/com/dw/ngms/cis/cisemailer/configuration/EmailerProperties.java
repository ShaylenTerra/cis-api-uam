package com.dw.ngms.cis.cisemailer.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author : prateekgoel
 * @since : 27/05/21, Thu
 **/
@Configuration
@ConfigurationProperties(prefix = "cis-emailer", ignoreUnknownFields = false)
@Data
public class EmailerProperties {

    private final Mail mail = new Mail();

    @Data
    public class Mail {
        private String[] bcc;
        private String emailFrom;
    }

}
