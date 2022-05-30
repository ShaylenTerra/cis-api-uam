package com.dw.ngms.cis.persistence.domains.lodgement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "LDG_DRAFT_DOCUMENT")
public class LodgementDraftDocument {

    @Id
    @GeneratedValue(generator = "ldg_draft_document_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "ldg_draft_document_seq",sequenceName = "ldg_draft_document_seq", allocationSize = 1)
    @Column(name = "DOCUMENTID")
    private Long documentId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "STEP_ID", nullable = false)
    private LodgementDraftStep lodgementDraftStep;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "REQUESTID", nullable = false)
    private LodgementDraftRequest lodgementDraftRequest;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DRAFT_ID", nullable = false)
    private LodgementDraft lodgementDraft;

    @Column(name = "DOCUMENT_ITEMID")
    private Long documentItemId;

    @Column(name = "PURPOSE_ITEMID")
    private Long purposeItemId;

    @Column(name = "USERID")
    private Long userId;

    @Column(name = "DATED")
    private LocalDateTime dated;

    @Column(name = "NOTES")
    private String notes;

    @Column(name = "DOCUMENT_NAME")
    private String documentName;

}
