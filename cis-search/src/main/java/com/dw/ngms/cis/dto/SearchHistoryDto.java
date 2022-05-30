package com.dw.ngms.cis.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author : prateekgoel
 * @since : 10/03/21, Wed
 **/
@Data
public class SearchHistoryDto {

    @NotNull
    private Long userId;

    @NotNull
    private Long provinceId;

    private Long searchTypeId;

    private Long searchFilterId;

    private String caption;

    private String data;

}
