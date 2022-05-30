package com.dw.ngms.cis.service;

import com.dw.ngms.cis.configuration.AppPropertiesConfig;
import com.dw.ngms.cis.persistence.domains.system.SystemConfiguration;
import com.dw.ngms.cis.persistence.repository.system.SystemConfigurationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;

/**
 * @author : prateekgoel
 * @since : 19/11/20, Thu
 **/
@Service
@Slf4j
@AllArgsConstructor
public class SmsService {

    private final RestTemplate restTemplate;

    private final AppPropertiesConfig appPropertiesConfig;

    private final SystemConfigurationRepository systemConfigurationRepository;


    public String sendSms(String phoneNumber, String smsBody) {

        SystemConfiguration sms_configuration = systemConfigurationRepository.findByTag("SMS_CONFIGURATION");

        String tagValue = sms_configuration.getTagValue();

        if(!tagValue.equalsIgnoreCase("YES")) {
            log.debug("Sms are not configured for application by super user");
            return null;
        }

        String request = MessageFormat.format("{0}user={1}&password={2}&sender={3}&SMSText={4}&GSM={5}",
                appPropertiesConfig.getSms().getBaseUrl(),
                appPropertiesConfig.getSms().getUsername(),
                appPropertiesConfig.getSms().getPassword(),
                appPropertiesConfig.getSms().getSender(),
                smsBody,
                phoneNumber);
        log.debug(" Sending request {} to sms gateway ", request);

        ResponseEntity<String> response = restTemplate
                .getForEntity(request, String.class);

        log.debug("response {} from sms gateway", response.getBody() );

        return response.getStatusCode() == HttpStatus.OK ? response.getBody(): null;
    }
}
