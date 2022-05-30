package com.dw.ngms.cis.service.dto.workflow;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collection;

/**
 * @author : prateekgoel
 * @since : 28/12/20, Mon
 **/
@Data
public class ReassignWorkflowDto {

    @NotNull
    private Long loggedInUser;

    @NotNull
    @Size(min = 1)
    private Collection<Long> actionIds;

    @NotNull
    private Long reassignedToUser;

    @NotNull
    private String notes;

}
