package com.dw.ngms.cis.web.vm;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author : prateekgoel
 * @since : 26/06/21, Sat
 **/
@Data
public class SearchDetailsShareVm {

    @NotNull
    private Long userId;

    @NotNull
    private String emailId;

    @NotNull
    private Long recordId;
}
