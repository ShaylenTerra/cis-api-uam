package com.dw.ngms.cis.service.dto.fee;

import lombok.Data;

/**
 * @author : prateekgoel
 * @since : 27/11/20, Fri
 **/
@Data
public class FeeTypeDto {

    private Long id;

    private String feeType;

    private String description;

    private Integer isActive;
}
