package com.dw.ngms.cis.cisworkflow.rest.request;

import lombok.Data;

/**
 * @author : prateekgoel
 * @since : 28/05/21, Fri
 **/
@Data
public class WorkflowProcessRequest {

    private Long actionTakenId;
    private Long processId;
    private Long loggedUserId;
    private String notes;
    private String context;
    private Integer type;
    private String processData;
    private Long currentNodeId;
    private Long actionId;
    private Long assignedToUserId;

}
