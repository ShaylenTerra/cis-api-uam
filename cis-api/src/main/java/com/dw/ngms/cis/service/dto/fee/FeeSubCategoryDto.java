package com.dw.ngms.cis.service.dto.fee;

import lombok.Data;

/**
 * @author : prateekgoel
 * @since : 27/11/20, Fri
 **/
@Data
public class FeeSubCategoryDto {

    private Long feeSubCategoryId;

    private Long categoryId;

    private String name;

    private String description;

    private Integer isActive;

}
