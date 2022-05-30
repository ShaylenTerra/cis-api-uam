package com.dw.ngms.cis.persistence.domains.fee;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author : prateekgoel
 * @since : 27/11/20, Fri
 **/
@Data
@Entity
@Table(name = "FEE_SCHEDULAR")
public class FeeScale {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fee_schedular-seq")
    @SequenceGenerator(name = "fee_schedular-seq", sequenceName = "FEE_SCHEDULAR_SEQ", allocationSize = 1)
    @Column(name = "FEESCHEDULEID")
    private Long feeScaleId;

    @Column(name = "EFFECTIVEDATE")
    private LocalDateTime effectiveDate;

    @Column(name = "USERID")
    private Long userId;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CREATEDON")
    private LocalDateTime createOn = LocalDateTime.now();

    @Column(name = "NAME")
    private String feeScaleName;

    @Column(name = "DOCUMENT_NAME")
    private String documentName;
}
