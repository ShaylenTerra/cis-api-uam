package com.dw.ngms.cis.persistence.domains.lodgement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "LDG_BATCH")
public class LodgementBatch {

    @Id
    @GeneratedValue(generator = "ldg_batch_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "ldg_batch_seq",sequenceName = "ldg_batch_seq", allocationSize = 1)
    @Column(name = "BATCH_ID")
    private Long batchId;

    @Column(name = "BATCH_NUMBER")
    private Long batchNumber;

    @Column(name = "CREATED_ON")
    private LocalDateTime createdOn;

    @Column(name = "USERID")
    private Long userId;

    @Column(name = "PROVINCE_ID")
    private Long provinceId;

    @Column(name = "DRAFT_ID", nullable = false)
    private Long draftId;

    @Lob
    @Column(name = "BATCH_NUMBER_TEXT")
    private String batchNumberText;

    @OneToMany(mappedBy = "lodgementBatch",
            fetch=FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @OrderBy("docTypeItemId ASC, docNumber ASC")
    private Set<LodgementBatchSgDocument> lodgementBatchSgDocuments;

}
