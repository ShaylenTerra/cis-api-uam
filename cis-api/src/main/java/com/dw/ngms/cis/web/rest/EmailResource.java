package com.dw.ngms.cis.web.rest;

import com.dw.ngms.cis.configuration.AppConstants;
import com.dw.ngms.cis.service.EmailServiceHandler;
import com.dw.ngms.cis.web.annotation.BaseResponse;
import com.dw.ngms.cis.web.vm.EmailVm;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author : prateekgoel
 * @since : 09/03/21, Tue
 **/
@BaseResponse
@AllArgsConstructor
@RestController
@RequestMapping(AppConstants.API_BASE_MAPPING + "/email")
public class EmailResource {


    private final EmailServiceHandler emailServiceHandler;

    /**
     * @param emailVm {@link EmailVm}
     */
    @PostMapping("/send")
    public void sendEmail(@RequestBody @Valid EmailVm emailVm) {
        emailServiceHandler.sendExternalEmail(emailVm);
    }

}
