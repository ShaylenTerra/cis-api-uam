package com.dw.ngms.cis.persistence.domains.lodgement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "LDG_DRAFT_PAYMENT")
public class LodgementDraftPayment {

    @Id
    @GeneratedValue(generator = "ldg_draft_payment_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "ldg_draft_payment_seq",sequenceName = "ldg_draft_payment_seq", allocationSize = 1)
    @Column(name = "PAYID")
    private Long payId;

    @Column(name = "PAYDATE")
    private LocalDateTime payDate;

    @Column(name = "AMOUNT")
    private Double amount;

    @Column(name = "REF_NUMBER")
    private String refNumber;

    @Column(name = "NOTES")
    private String notes;

    @Column(name = "PAY_METHOD_ITEMID")
    private Long payMethodItemId;

    @Column(name = "USERID")
    private Long userId;

    @Column(name = "STATUS_ITEMID")
    private Long statusItemId;

    @Column(name = "DATED")
    private LocalDateTime dated;

    @Column(name = "RECEIPT_NO")
    private String receiptNo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "DRAFT_ID", nullable = false)
    private LodgementDraft lodgementDraft;

    @Column(name = "DOC_NAME")
    private String docName;
}
