package com.dw.ngms.cis.vms;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author : prateekgoel
 * @since : 12/01/21, Tue
 **/
@Data
public class SearchVm {

    @NotNull
    private Long userId;

    @NotNull
    private Long provinceId;

    @NotNull
    private String provinceShortName;

    @NotNull
    private Long searchTypeId;

    @NotNull
    private Long searchFilterId;
}
