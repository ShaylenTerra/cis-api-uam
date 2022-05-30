package com.dw.ngms.cis.web.vm;

import com.dw.ngms.cis.service.dto.EmailDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Data;

/**
 * @author : prateekgoel
 * @since : 29/05/21, Sat
 **/
@Data
public class InvoiceTemplateVm {

    private Long workflowId;

    @JsonProperty("email")
    private EmailDto emailDto;
}
