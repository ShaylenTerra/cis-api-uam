package com.dw.ngms.cis.web.rest;

import com.dw.ngms.cis.configuration.AppConstants;
import com.dw.ngms.cis.service.SmsService;
import com.dw.ngms.cis.web.annotation.BaseResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * @author : prateekgoel
 * @since : 19/11/20, Thu
 * <p>
 * Controller resource to send sms notification for users
 **/
@BaseResponse
@RestController
@RequestMapping(AppConstants.API_BASE_MAPPING)
public class SmsResource {

    public SmsService smsService;

    public SmsResource(SmsService smsService) {
        this.smsService = smsService;
    }

    /**
     * :GET /?phoneNumber=?&smsBody=?
     * this method takes phone number for sms and text for sms
     * and return response from sms gateway provider
     *
     * @param phoneNumber 10 digit phone number
     * @param smsBody  sms text to be sent for
     * @return org.springframework.http.ResponseEntity
     */
    @GetMapping("/sms")
    public ResponseEntity<String> sendSms(@RequestParam("phoneNumber") @NotNull String phoneNumber,
                                        @RequestParam("smsBody") String smsBody) {
        String response = smsService.sendSms(phoneNumber, smsBody);
        return ResponseEntity.ok().body(response);
    }

}
