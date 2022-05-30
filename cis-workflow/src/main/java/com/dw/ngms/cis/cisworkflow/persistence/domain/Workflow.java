package com.dw.ngms.cis.cisworkflow.persistence.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author : prateekgoel
 * @since : 20/05/21, Thu
 **/
@Data
@Entity
@Table(name = "WORKFLOW")
public class Workflow {

    @Id
    @Column(name = "WORKFLOWID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WORKFLOW_SEQUENCE_GENERATOR")
    @SequenceGenerator(name = "WORKFLOW_SEQUENCE_GENERATOR", sequenceName = "WORKFLOW_SEQ", allocationSize = 1)
    private Long workflowId;

    @Column(name = "PROCESSID")
    private Long processId;

    @Column(name = "TRIGGEREDON")
    private LocalDateTime triggeredOn;

    @Column(name = "STATUSID")
    private Integer statusId;

    @Column(name = "REFERENCE_NO")
    private String referenceNo;

    @Column(name = "TRANSACTIONID")
    private Long transactionId;

    @Column(name = "PARENT_WORKFLOWID")
    private Long parentWorkflowId;

    @Column(name = "COMPLETEDON")
    private LocalDateTime completedOn;

    @Column(name = "PROVINCEID")
    private Long provinceId;

    @Column(name = "INTERNAL_STATUS_ID")
    private Integer internalStatusId;

    @Column(name = "EXTERNAL_STATUS_ID")
    private Integer externalStatusId;

    @Column(name = "TRUNAROUND_DURATION")
    private Long turnAroundDuration;

    @Column(name = "PROCESSDATA")
    private String processData;

    @Column(name = "PRIORITY_FLAG")
    private Long priorityFlag;

}
