package com.dw.ngms.cis.persistence.domains.workflow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @author : prateekgoel
 * @since : 29/12/20, Tue
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "WORKFLOW_DOCUMENTS")
public class WorkflowDocuments {

    @Id
    @Column(name = "DOCUMENTID")
    @GeneratedValue(generator = "workflow_document_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "workflow_document_seq", sequenceName = "WORKFLOW_DOCUMENT_SEQ", allocationSize = 1)
    private Long documentId;

    @Column(name = "DOCNAME")
    private String docName;

    @Column(name = "UPLODEDON")
    private Date uploadedOn;

    @Column(name = "USERID")
    private Long userId;

    @Column(name = "DOCUMENTTYPEID")
    private Long documentTypeId;

    @Column(name = "NOTES")
    private String notes;

    @Column(name = "EXTENSION")
    private String extension;

    @Column(name = "SIZE_KB")
    private Long sizeKb;

    @Column(name = "WORKFLOWID")
    private Long workflowId;

    @Column(name = "UPLOAD_REF_ID")
    private Long uploadRefId;

}
