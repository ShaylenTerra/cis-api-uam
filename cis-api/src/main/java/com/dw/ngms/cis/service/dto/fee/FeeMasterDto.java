package com.dw.ngms.cis.service.dto.fee;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author : prateekgoel
 * @since : 27/11/20, Fri
 **/
@Data
public class FeeMasterDto {

    private Long feeId;

    @NotNull
    private Long fee;

    @NotNull
    private Integer isActive;

    @NotNull
    private Long feeSubCategoryId;

    @NotNull
    private Long feeScaledId;

    @NotNull
    private Long feeTypeId;

}
