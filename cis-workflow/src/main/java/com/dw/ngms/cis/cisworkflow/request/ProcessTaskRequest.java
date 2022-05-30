package com.dw.ngms.cis.cisworkflow.request;

import com.dw.ngms.cis.cisworkflow.rest.request.WorkflowProcessRequest;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author : prateekgoel
 * @since : 20/05/21, Thu
 **/
@Data
public class ProcessTaskRequest {

    private Long actionTakenId;

    private Long processId;

    private Long loggedUserId;

    private String notes;

    private String context;

    private Integer type;

    private String processData;

    private Long currentNodeId;

    private Long actionId;

    private Long workflowId;

    private Long assignedtouserid;

    private LocalDateTime date;

    public void setRequestData(final WorkflowProcessRequest workflowProcessingRequestParameter) {
        this.actionId = workflowProcessingRequestParameter.getActionId();
        this.context = workflowProcessingRequestParameter.getContext();
        this.currentNodeId = workflowProcessingRequestParameter.getCurrentNodeId();
        this.loggedUserId = workflowProcessingRequestParameter.getLoggedUserId();
        this.notes = workflowProcessingRequestParameter.getNotes();
        this.processData = workflowProcessingRequestParameter.getProcessData();
        this.processId = workflowProcessingRequestParameter.getProcessId();
        this.type = workflowProcessingRequestParameter.getType();
        this.actionTakenId = workflowProcessingRequestParameter.getActionTakenId();
        this.assignedtouserid = workflowProcessingRequestParameter.getAssignedToUserId();
        this.date = LocalDateTime.now();
    }

}
