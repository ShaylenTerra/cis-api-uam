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
@Table(name = "LDG_BATCH_SG_DOC")
public class LodgementBatchSgDocument {

    @Id
    @GeneratedValue(generator = "ldg_batch_sg_doc_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "ldg_batch_sg_doc_seq",sequenceName = "ldg_batch_sg_doc_seq", allocationSize = 1)
    @Column(name = "SG_DOCUMENT_ID")
    private Long sgDocumentId;


    @Column(name = "DOC_NUMBER")
    private Long docNumber;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "BATCH_ID", nullable = false)
    private LodgementBatch lodgementBatch;

    @Column(name = "DOC_TYPE_ITEMID")
    private Long docTypeItemId;

    @Column(name = "PROVINCE_ID")
    private Long provinceId;

    @Lob
    @Column(name = "DOC_NUMBER_TEXT")
    private String docNumberText;

    @Column(name = "RES_OUTCOME_ID")
    private Long resOutcomeId;

    @Column(name = "CREATED_ON")
    private LocalDateTime createdOn;

}
