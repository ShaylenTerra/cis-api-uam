package com.dw.ngms.cis.web.vm.workflow;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author : prateekgoel
 * @since : 28/01/21, Thu
 **/
@Data
public class WorkflowChangeProvinceVM {

    @NotNull
    private String provinceShortName;

    @NotNull
    private Long toBeChangedProvinceId;

    @NotNull
    private Long workflowId;
}
