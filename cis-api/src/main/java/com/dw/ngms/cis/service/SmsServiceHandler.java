package com.dw.ngms.cis.service;

import com.dw.ngms.cis.cisemailer.util.MailUtils;
import com.dw.ngms.cis.persistence.domains.Template;
import com.dw.ngms.cis.persistence.domains.User;
import com.dw.ngms.cis.persistence.domains.lodgement.LodgementDraft;
import com.dw.ngms.cis.persistence.domains.workflow.Workflow;
import com.dw.ngms.cis.persistence.repository.TemplateRepository;
import com.dw.ngms.cis.persistence.repository.UserRepository;
import com.dw.ngms.cis.persistence.repository.lodgement.LodgementDraftRepository;
import com.dw.ngms.cis.persistence.repository.workflow.WorkflowRepository;
import com.dw.ngms.cis.service.dto.SmsDto;
import com.dw.ngms.cis.utilities.TemplateUtils;
import com.dw.ngms.cis.web.vm.LodgementNotificationVm;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author prateek on 21-04-2022
 */
@Service
@AllArgsConstructor
public class SmsServiceHandler {

    private final SmsService smsService;

    private final TemplateRepository templateRepository;

    private final UserRepository userRepository;

    private final TemplateUtils templateUtils;

    @Async
    public void sendLodgementSmsNotification(final Long userId,
                                             final Long templateId,
                                             final String referenceNo) {

        Map<String, String> placeholder = new HashMap<>();

        placeholder.put("referenceNumber", referenceNo);

        User userByUserId = userRepository.findUserByUserId(userId);

        if (null != userByUserId) {

            placeholder.put("firstName", userByUserId.getFirstName());

            placeholder.put("surName", userByUserId.getSurname());

            Template byTemplateId = templateRepository.findByTemplateId(templateId);

            if (null != byTemplateId) {

                String smsDetails = byTemplateId.getSmsDetails();

                String smsBodyTemplate = MailUtils.substituteStringToken(smsDetails, placeholder);

                SmsDto smsBody = templateUtils.getSmsFromSmsTemplate(smsBodyTemplate);

                String mobileNo = userByUserId.getMobileNo();

                if(StringUtils.isNotBlank(mobileNo) && null != smsBody) {

                    smsService.sendSms(mobileNo, smsBody.getBody());
                }

            }
        }
    }
}
