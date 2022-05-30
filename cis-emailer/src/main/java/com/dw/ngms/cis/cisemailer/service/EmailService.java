package com.dw.ngms.cis.cisemailer.service;

import com.dw.ngms.cis.cisemailer.configuration.EmailerProperties;
import com.dw.ngms.cis.cisemailer.util.MailUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author : prateekgoel
 * @since : 27/05/21, Thu
 **/
@Service
@Slf4j
public class EmailService implements MailSender {

    private final Environment env;

    private final EmailerProperties emailerProperties;

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailService(Environment env,
                        EmailerProperties emailerProperties,
                        JavaMailSender javaMailSender) {
        this.env = env;
        this.emailerProperties = emailerProperties;
        this.javaMailSender = javaMailSender;
    }

    public String getMailBodyContent(final String template, final Map<String, String> placeHolders) {
        if (null == template)
            return null;

        log.debug(" content {} substituting token {}", template, placeHolders);
        final String replace = MailUtils.substituteStringToken(template, placeHolders);
        try (final InputStream inputStream = new ClassPathResource("email.html").getInputStream();
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream))) {
            final String collect = bufferedReader.lines().collect(Collectors.joining(" "));
            Map<String, String> bodyMap = new HashMap<>();
            bodyMap.put("body", replace);
            final String profile = String.join(",", env.getActiveProfiles());
            bodyMap.put("profile", profile);
            LocalDateTime now = LocalDateTime.now();
            String formattedDate = now.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm a"));
            bodyMap.put("date", formattedDate);
            return MailUtils.substituteStringToken(collect, bodyMap);


        } catch (Exception e) {
            log.error(" Exception occurs while parsing email template {}", e.getMessage());
        }
        return null;
    }


    public Mail getMailContent(final String template, final Map<String, String> placeHolders) {
        if (null == template)
            return null;

        final String mailBodyContent = getMailBodyContent(template, placeHolders);

        return Mail.builder()
                .body(mailBodyContent)
                .build();
    }

    @Override
    public void sendMail(Mail mail) {
        log.debug("Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content {}", mail.isMultipart(),
                mail.isHtml(), mail.getTo(), mail.getSubject(), mail.getBody());

        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, mail.isMultipart(), StandardCharsets.UTF_8.name());
            message.setTo(mail.getTo());
            String[] cc = mail.getCc();
            if (ArrayUtils.isNotEmpty(cc)) {
                message.setCc(cc);
            }
            message.setBcc(emailerProperties.getMail().getBcc());
            message.setFrom(emailerProperties.getMail().getEmailFrom());
            message.setSubject(mail.getSubject());
            message.setText(mail.getBody(), mail.isHtml());
            if (mail.isMultipart() && !CollectionUtils.isEmpty(mail.getResources())) {
                mail.getResources().forEach(resource -> {
                    try {

                        message.addAttachment(resource.getFilename(), resource);

                    } catch (MessagingException e) {
                        log.error("error while attaching file in email");
                        e.printStackTrace();
                    }
                });

            }
            javaMailSender.send(mimeMessage);
            log.debug("Sent email to User '{}'", mail.getTo());
        } catch (MailException | MessagingException e) {
            log.warn("Email could not be sent to user '{}' due to {}", mail.getTo(), e.getMessage());
        }
    }
}
