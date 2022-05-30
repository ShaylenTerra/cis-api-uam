package com.dw.ngms.cis.cisworkflow.persistence.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "WORKFLOWPROCESS_SCREEN")
public class WorkflowProcessScreen {

    @Id
    @Column(name = "SCREENID")
    private Long screenId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ISACTIVE")
    private Long isActive;

    @Column(name = "ITEMID_MODULE")
    private Long itemIdModule;

}
