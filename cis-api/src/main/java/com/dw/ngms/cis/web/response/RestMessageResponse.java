package com.dw.ngms.cis.web.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RestMessageResponse {
    private String message;

}