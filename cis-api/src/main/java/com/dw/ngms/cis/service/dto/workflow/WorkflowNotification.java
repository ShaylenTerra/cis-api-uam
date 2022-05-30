package com.dw.ngms.cis.service.dto.workflow;

import lombok.Builder;
import lombok.Data;

/**
 * @author : prateekgoel
 * @since : 03/01/21, Sun
 **/
@Data
@Builder
public class WorkflowNotification {

    private Long templateId;

    private String fullName;

    private String emailId;

    private Long workflowId;

    private Long transactionId;

    private String referenceNo;
}
