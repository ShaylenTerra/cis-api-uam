package com.dw.ngms.cis.persistence.domains;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author : prateekgoel
 * @since : 06/01/21, Wed
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PAYMENT")
public class Payment {

    @Id
    @Column(name = "PAYMENT_ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "payment_sequence")
    @SequenceGenerator(name = "payment_sequence", sequenceName = "PAYMENT_SEQ", allocationSize = 1)
    private Long paymentId;

    @Column(name = "PAYMENT_REFERENCE_ID")
    private String paymentReferenceId;

    @Column(name = "PAYMENT_DATE")
    private LocalDateTime paymentDate;

    @Column(name = "LAST_UPDATED_ON")
    private LocalDateTime lastUpdatedOn;

    @Column(name = "PAYMENT_AMOUNT", precision = 10, scale = 2)
    private Double paymentAmount;

    @Column(name = "PAYMENT_DOCUMENT_ID")
    private Long paymentDocumentId;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "WORKFLOW_ID")
    private Long workflowId;

    @Column(name = "PAYMENT_COMMENTS")
    private String paymentComments;

    @Column(name = "INVOICE_AMOUNT", precision = 10, scale = 2)
    private Double invoiceAmount;

    @Column(name = "INVOICE_DUE_DATE", columnDefinition = "TIMESTAMP")
    private LocalDateTime invoiceDueDate;

    @Column(name = "INVOICE_GENERATION_DATE", columnDefinition = "TIMESTAMP")
    private LocalDateTime invoiceGenerationDate;

    @Column(name = "INVOICE_COMMENT")
    private String invoiceComment;

    @Lob
    @Column(name = "INVOICE_EMAIL")
    private String invoiceEmail;
}
