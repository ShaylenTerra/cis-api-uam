package com.dw.ngms.cis.persistence.projection;

import lombok.Data;

/**
 * @author : prateekgoel
 * @since : 04/12/20, Fri
 **/
@Data
public class FeeMasterProjection {

    private Long subCategoryId;
    private String fee;
    private Long feeId;
    private Boolean isActive;
    private String feeType;
}
