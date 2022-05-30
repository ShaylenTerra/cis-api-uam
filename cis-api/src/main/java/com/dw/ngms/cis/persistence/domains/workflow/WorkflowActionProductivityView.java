package com.dw.ngms.cis.persistence.domains.workflow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "VW_WF_ACTION_PRODUCTIVITY")
public class WorkflowActionProductivityView {

    @Id
    @Column(name = "ACTIONID", updatable = false, insertable = false)
    private Long actionid;

    @Column(name = "WORKFLOWID")
    private Long workflowId;

    @Column(name = "ACTIONREQUIRED")
    private Long actionrequired;

    @Column(name = "ACTION_REQUIRED")
    private String actionRequiredText;

    @Column(name = "ACTIONTAKEN")
    private Long actiontaken;

    @Column(name = "POSTEDON")
    private LocalDateTime postedon;

    @Column(name = "USERID")
    private Long userid;

    @Column(name = "FULLNAME")
    private String fullname;

    @Column(name = "ACTEDON")
    private LocalDateTime actedon;

    @Column(name = "NODEID")
    private Long nodeid;

    @Column(name = "PRODUCTIVITY_DATE")
    private LocalDate productivityDate;

    @Column(name = "PRODUCTIVITY_MINUTES")
    private Long productivityMinutes;

    @Column(name = "CONTEXT")
    private String context;

}
