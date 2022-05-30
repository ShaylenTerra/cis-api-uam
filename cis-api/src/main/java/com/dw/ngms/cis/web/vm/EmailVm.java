package com.dw.ngms.cis.web.vm;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @author : prateekgoel
 * @since : 09/03/21, Tue
 **/
@Data
public class EmailVm {

    @NotNull
    private Long templateId;

    @NotNull
    private String sendToEmailId;

    @NotNull
    private Map<String, String> contentEntry;

}
