package com.dw.ngms.cis.web.vm;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author : prateekgoel
 * @since : 29/12/20, Tue
 **/
@Data
public class AddDiariseDateVm {

    @NotNull
    private Date diariseDate;

    @NotNull
    private Long workflowId;

    @NotNull
    private Long userId;

    @NotNull
    private String comment;

    @NotNull
    private Long actionId;
}
