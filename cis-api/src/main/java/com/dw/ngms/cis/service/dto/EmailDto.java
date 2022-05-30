package com.dw.ngms.cis.service.dto;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Data;

/**
 * @author : prateekgoel
 * @since : 29/05/21, Sat
 **/
@Data
public class EmailDto {

    private String subject;

    private String body;
}
