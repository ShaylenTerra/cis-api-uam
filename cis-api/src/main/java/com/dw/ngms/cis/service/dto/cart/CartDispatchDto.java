package com.dw.ngms.cis.service.dto.cart;

import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author : prateekgoel
 * @since : 11/02/21, Thu
 **/
@Data
public class CartDispatchDto {

    private Long cartDispatchId;

    @NotNull
    private Long workflowId;

    @NotNull
    @JsonRawValue
    private String dispatchDetails;
}
