package com.dw.ngms.cis.utilities;

import com.dw.ngms.cis.service.dto.EmailDto;
import com.dw.ngms.cis.service.dto.SmsDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.w3c.tidy.Tidy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

/**
 * @author : prateekgoel
 * @since : 11/02/21, Thu
 **/
@Component
@AllArgsConstructor
@Slf4j
public final class TemplateUtils {

    private static final String UTF_8 = "UTF-8";

    private final ObjectMapper objectMapper;

    public static String convertToXhtml(String html) throws UnsupportedEncodingException {
        Tidy tidy = new Tidy();
        tidy.setInputEncoding(UTF_8);
        tidy.setOutputEncoding(UTF_8);
        tidy.setXHTML(true);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(html.getBytes(StandardCharsets.UTF_8));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        tidy.parseDOM(inputStream, outputStream);
        return outputStream.toString(UTF_8);
    }

    public EmailDto getEmailFromEmailTemplate(final String emailTemplate) {
        try {

            return objectMapper.readValue(emailTemplate, EmailDto.class);

        } catch (JsonProcessingException e) {
            log.error("error occurred while getting emailDto from emailTemplate {}", emailTemplate);
            e.printStackTrace();
        }
        return null;
    }

    public SmsDto getSmsFromSmsTemplate(final String smsTemplate) {
        try{

            return objectMapper.readValue(smsTemplate, SmsDto.class);

        }catch (JsonProcessingException e) {
            log.error("error occurred while getting emailDto from emailTemplate {}", smsTemplate);
            e.printStackTrace();
        }
        return null;
    }

}
