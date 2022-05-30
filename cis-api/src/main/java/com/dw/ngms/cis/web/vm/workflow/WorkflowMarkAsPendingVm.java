package com.dw.ngms.cis.web.vm.workflow;

import lombok.Data;

/**
 * @author : prateekgoel
 * @since : 28/01/21, Thu
 **/
@Data
public class WorkflowMarkAsPendingVm {

    private String notes;

    private Long userId;

    private Long workflowId;

}
