package com.dw.ngms.cis.persistence.domains.workflow;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
@Entity
@Table(name = "WORKFLOW_NOTES")
public class WorkflowNotes {

    @Id
    @Column(name = "NOTEID")
    @GeneratedValue(generator = "workflow_notes_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "workflow_notes_seq", sequenceName = "WORKFLOW_NOTES_SEQ", allocationSize = 1)
    private Long noteId;

    @Column(name = "TRANSACTIONID")
    private Long transactionId;

    @Column(name = "WORKFLOWID")
    private Long workflowId;

    @Column(name = "CONTEXT")
    private String context;

    @Column(name = "NOTE")
    private String note;
}
