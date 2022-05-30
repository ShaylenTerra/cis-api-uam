package com.dw.ngms.cis.cisemailer.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author : prateekgoel
 * @since : 27/05/21, Thu
 **/
@Configuration
@RequiredArgsConstructor
public class MailConfiguration {

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.port}")
    private Integer port;

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;


    @Bean
    public JavaMailSender javaMailSenderImpl() {
        JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
        javaMailSenderImpl.setHost(host);
        javaMailSenderImpl.setPort(port);
        javaMailSenderImpl.setUsername(username);
        javaMailSenderImpl.setPassword(password);
        Properties javaMailProperties = new Properties();
        javaMailProperties.put("mail.debug", true);
        javaMailProperties.put("mail.smtp.auth", true);
        javaMailProperties.put("mail.ssl.enabled", true);
        javaMailProperties.put("mail.smtp.starttls.enable", true);
        javaMailProperties.put("mail.transport.protocol", "smtp");
        javaMailSenderImpl.setJavaMailProperties(javaMailProperties);
        return javaMailSenderImpl;
    }
}
