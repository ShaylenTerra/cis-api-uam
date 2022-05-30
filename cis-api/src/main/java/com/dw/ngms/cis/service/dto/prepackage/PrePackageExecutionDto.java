package com.dw.ngms.cis.service.dto.prepackage;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author : prateekgoel
 * @since : 23/03/21, Tue
 **/
@Data
public class PrePackageExecutionDto {

    private Long execId;

    private Long subscriptionId;

    private LocalDateTime executionDate;

    private String status;

    private LocalDateTime nextExecutionDate;

    private String message;

    private String ftpLocation;

    private String notificationStatus;

}
