package com.dw.ngms.cis.persistence.views;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "VW_WORKFLOW_ACTION_DATA")
public class VwWorkflowActionData {

    @Id
    @Column(name = "WORKFLOWID")
    private Long workflowId;

    @Column(name = "ACTIONID")
    private Long actionId;

    @Column(name = "ACTIONREQUIRED")
    private Long actionrequired;

    @Column(name = "ACTION_REQUIRED")
    private String actionRequired;

    @Column(name = "ACTIONTAKEN")
    private Long actiontaken;

    @Column(name = "POSTEDON")
    private LocalDateTime postedOn;

    @Column(name = "USERID")
    private Long userId;

    @Column(name = "FULLNAME")
    private String fullName;

    @Column(name = "ACTEDON")
    private LocalDateTime actedOn;

    @Column(name = "NODEID")
    private Long nodeId;

    @Column(name = "PRODUCTIVITY_MINUTES")
    private Long productivityMinutes;

}
