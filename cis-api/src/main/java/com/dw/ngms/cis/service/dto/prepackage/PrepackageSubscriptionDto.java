package com.dw.ngms.cis.service.dto.prepackage;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author : prateekgoel
 * @since : 08/01/21, Fri
 **/
@Data
public class PrepackageSubscriptionDto {

    private Long subscriptionId;

    private String referenceId;

    @NotNull
    private Long prePackageId;

    @NotNull
    private Long userId;

    @NotNull
    private String frequencyId;

    @NotNull
    private Long locationTypeId;

    private String locationId;

    private String location;

    @NotNull
    private Integer subscriptionStatus;

}
