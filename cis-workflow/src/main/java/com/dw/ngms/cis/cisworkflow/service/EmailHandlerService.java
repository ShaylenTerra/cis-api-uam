package com.dw.ngms.cis.cisworkflow.service;

import com.dw.ngms.cis.cisemailer.service.EmailService;
import com.dw.ngms.cis.cisemailer.service.Mail;
import com.dw.ngms.cis.cisworkflow.persistence.domain.Email;
import com.dw.ngms.cis.cisworkflow.persistence.domain.MTemplates;
import com.dw.ngms.cis.cisworkflow.persistence.domain.User;
import com.dw.ngms.cis.cisworkflow.persistence.repository.TemplatesRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : prateekgoel
 * @since : 07/06/21, Mon
 **/
@Service
@AllArgsConstructor
@Slf4j
public class EmailHandlerService {

    private final EmailService  emailService;

    private final TemplatesRepository templatesRepository;

    private final ObjectMapper objectMapper;

    public void triggerTaskEmail(final User user,
                                 final String referenceNo,
                                 final String actionName,
                                 final String processName) {



        try {

            final MTemplates task_action = templatesRepository.findByTemplateName("TASK ACTION");

            final String emailDetails = task_action.getEmailDetails();

            final Email email = objectMapper.readValue(emailDetails, Email.class);

            final String body = email.getBody();

            Map<String,String> placeholder = new HashMap<>();
            placeholder.put("firstName", user.getFirstName());
            placeholder.put("surName", user.getSurName());
            placeholder.put("referenceNumber", referenceNo);
            placeholder.put("actionName", actionName);
            placeholder.put("processName", processName);
            final Mail mail = emailService.getMailContent(body, placeholder);
            mail.setSubject(email.getSubject());
            mail.setMultipart(Boolean.FALSE);
            mail.setHtml(Boolean.TRUE);
            mail.setTo(user.getEmail());

            //emailService.sendMail(mail);

        } catch (Exception e) {
            e.printStackTrace();
            log.error("error occurred while parsing email template");
        }



    }

}
