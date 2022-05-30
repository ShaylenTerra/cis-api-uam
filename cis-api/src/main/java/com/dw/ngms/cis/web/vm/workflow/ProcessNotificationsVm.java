package com.dw.ngms.cis.web.vm.workflow;

import lombok.Data;

/**
 * @author : prateekgoel
 * @since : 03/01/21, Sun
 **/
@Data
public class ProcessNotificationsVm {

    private Long templateId;

    private Long workflowId;

    private Long userId;

    private Long transactionId;

    private String referenceNo;

    private String fullName;

    private String email;

}
