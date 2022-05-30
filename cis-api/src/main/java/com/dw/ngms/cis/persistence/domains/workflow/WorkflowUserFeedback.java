package com.dw.ngms.cis.persistence.domains.workflow;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "WORKFLOW_USER_FEEDBACK")
public class WorkflowUserFeedback {

    @Id
    @GeneratedValue(generator = "workflow_user_feedback_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "workflow_user_feedback_seq", sequenceName = "workflow_user_feedback_seq", allocationSize = 1)
    @Column(name = "FEEDBACK_ID")
    private Long feedbackId;

    @Column(name = "WORKFLOWID")
    private Long workflowId;

    @Column(name = "RATING")
    private Long rating;

    @Column(name = "NOTES")
    private String notes;

    @Column(name = "TO_USERID")
    private Long toUserId;

    @Column(name = "FROM_USERID")
    private Long fromUserId;

    @Column(name = "DATED")
    private LocalDateTime dated;

}
