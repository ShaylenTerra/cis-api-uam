package com.dw.ngms.cis.service.dto.dispatch;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class DispatchDto {


    private Long dispatchId;

    @NotNull
    private Long workflowId;

    @NotNull
    @JsonRawValue
    private String dispatchDetails;
}
