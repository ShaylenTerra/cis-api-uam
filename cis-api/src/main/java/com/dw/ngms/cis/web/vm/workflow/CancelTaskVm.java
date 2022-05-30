package com.dw.ngms.cis.web.vm.workflow;

import lombok.Data;

/**
 * @author : prateekgoel
 * @since : 29/12/20, Tue
 **/
@Data
public class CancelTaskVm {

    private String notes;

    private Long userId;

    private Long workflowId;

    private Long actionId;

}
