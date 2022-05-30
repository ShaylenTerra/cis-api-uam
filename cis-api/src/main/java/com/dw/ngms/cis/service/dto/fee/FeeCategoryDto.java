package com.dw.ngms.cis.service.dto.fee;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author : prateekgoel
 * @since : 27/11/20, Fri
 **/
@Data
public class FeeCategoryDto {

    private Long feeCategoryId;

    @NotNull
    private String name;

    @NotNull
    private String description;

    private Integer isActive;

}
