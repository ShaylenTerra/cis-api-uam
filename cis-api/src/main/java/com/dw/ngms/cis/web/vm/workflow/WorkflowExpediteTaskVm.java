package com.dw.ngms.cis.web.vm.workflow;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author : prateekgoel
 * @since : 29/12/20, Tue
 **/
@Data
public class WorkflowExpediteTaskVm {

    @NotNull
    private Long workflowId;

    @NotNull
    private String notes;

    @NotNull
    private Long priorityFlag;

    @JsonIgnore
    private Long actionId;

    @NotNull
    private Long userId;

}
