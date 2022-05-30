package com.dw.ngms.cis.cisworkflow.persistence.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "WORKFLOWPROCESS_MATRIX")
public class WorkflowProcessMatrix {

    @Id
    @Column(name = "ID")
    private Long matrixId;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "TYPEFLAG")
    private String typeflag;

    @Column(name = "TAG")
    private String tag;

    @Column(name = "ACTIVE")
    private Long isActive;

}
