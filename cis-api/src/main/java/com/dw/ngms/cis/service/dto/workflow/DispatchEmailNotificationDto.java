package com.dw.ngms.cis.service.dto.workflow;

import lombok.Data;

/**
 * @author : prateekgoel
 * @since : 27/02/21, Sat
 **/
@Data
public class DispatchEmailNotificationDto {

    private String email;

    private Long workflowId;

    private String ftpUrl;

    private Long templateId;

    private String firstName;

    private String surName;

    private String dispatchMethod;

}
