package com.dw.ngms.cis.persistence.domains.reservation;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "RES_DRAFT_DOCUMENTS")
public class ReservationDraftDocument {

    @Id
    @Column(name = "DOCUMENT_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "res_draft_documents_seq")
    @SequenceGenerator(name = "res_draft_documents_seq", sequenceName = "RES_DRAFT_DOCUMENTS_SEQ", allocationSize = 1)
    private Long documentId;

    @Column(name = "TYPE_ID")
    private Long typeId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DATED")
    private LocalDateTime dated;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "draft_id", nullable = false)
    private ReservationDraft reservationDraft;

}
