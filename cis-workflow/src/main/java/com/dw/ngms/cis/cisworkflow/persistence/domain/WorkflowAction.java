package com.dw.ngms.cis.cisworkflow.persistence.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "WORKFLOW_ACTION")
public class WorkflowAction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "WORKFLOW_ACTION_SEQUENCE_GENERATOR")
    @SequenceGenerator(name = "WORKFLOW_ACTION_SEQUENCE_GENERATOR", sequenceName = "WORKFLOW_ACTION_SEQ", allocationSize = 1)
    @Column(name = "ACTIONID")
    private Long actionId;

    @Column(name = "WORKFLOWID")
    private Long workflowId;

    @Column(name = "ACTIONREQUIRED")
    private Long actionRequired;

    @Column(name = "POSTEDON")
    private LocalDateTime postedOn;

    @Column(name = "USERID")
    private Long userId;

    @Column(name = "ALLOCATEDON")
    private LocalDateTime allocatedOn;

    @Column(name = "ACTEDON")
    private LocalDateTime actedOn;

    @Column(name = "ACTIONTAKEN")
    private Long actionTaken;

    @Column(name = "TRANSACTIONID")
    private Long transactionId;

    @Column(name = "ACTIONTRANSACTIONID")
    private Long actionTransactionId;

    @Column(name = "LINKID")
    private Long linkId;

    @Column(name = "NODEID")
    private Long nodeId;

    @Column(name = "DATA_JSON")
    private String dataJson;

    @Column(name = "DIARISEDATE")
    private LocalDateTime diariseDate;

    @Column(name = "CONTEXT")
    private String context;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "NOTIFICATION_TIME")
    private Long notificationTime;

    @Column(name = "ESCALATION_TIME")
    private Long escalationTime;

    @Column(name = "TEMPLATEID")
    private Long templateId;


}
