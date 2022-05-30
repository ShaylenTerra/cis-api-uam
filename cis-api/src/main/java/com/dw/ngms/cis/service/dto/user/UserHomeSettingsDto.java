package com.dw.ngms.cis.service.dto.user;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author : prateekgoel
 * @since : 28/11/20, Sat
 **/
@Data
public class UserHomeSettingsDto {

    private Long settingId;

    @NotNull
    private Long userId;

    private String homepage;

    private String sections;
}
