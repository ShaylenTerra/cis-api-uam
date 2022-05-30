package com.dw.ngms.cis.persistence.domains.workflow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author : prateekgoel
 * @since : 29/12/20, Tue
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "WORKFLOWPROCESS_MATRIX")
public class WorkflowProcessMatrix {

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "workflow_notes_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "workflow_notes_seq", sequenceName = "WORKFLOW_NOTES_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "TYPEFLAG")
    private String typeFlag;

    @Column(name = "TAG")
    private String tag;

    @Column(name = "ACTIVE")
    private Long active;


}
