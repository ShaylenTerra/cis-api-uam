package com.dw.ngms.cis.service.dto;

import lombok.Data;

/**
 * @author : prateekgoel
 * @since : 18/05/21, Tue
 **/
@Data
public class SystemConfigurationDto {

    private Long id;

    private String tag;

    private String tagValue;

    private String caption;

    private Long userId;
}
