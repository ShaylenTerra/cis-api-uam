package com.dw.ngms.cis.service.dto.workflow;

import lombok.Data;

import java.time.LocalDateTime;


/**
 * @author : pragayanshu Kumar Shukla
 * @since : 28/03/22, Tue
 **/
@Data
public class WorkflowUserFeedbackDto {
    private Long feedbackId;
    private Long workflowId;
    private Long rating;
    private String notes;
    private Long toUserId;
    private Long fromUserId;
    private LocalDateTime dated;

}
