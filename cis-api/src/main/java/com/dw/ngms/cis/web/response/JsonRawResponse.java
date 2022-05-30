package com.dw.ngms.cis.web.response;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Builder;
import lombok.Data;

import java.util.Collection;

/**
 * @author : prateekgoel
 * @since : 02/01/21, Sat
 **/
@Data
@Builder
public class JsonRawResponse {

    @JsonRawValue
    public Collection<String> json;

}
