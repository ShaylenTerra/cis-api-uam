package com.dw.ngms.cis.cisworkflow.persistence.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author : prateekgoel
 * @since : 20/05/21, Thu
 **/
@Data
@Entity
@Table(name = "TRANSACTIONS")
public class Transactions {

    @Id
    @Column(name = "TRANSACTIONID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRANSACTIONS_SEQUENCE_GENERATOR")
    @SequenceGenerator(name = "TRANSACTIONS_SEQUENCE_GENERATOR", sequenceName = "TRANSACTIONS_SEQ", allocationSize = 1)
    private Long transactionId;

    @Column(name = "TTYPE")
    private Integer tType;

    @Column(name = "START_TIME")
    private LocalDateTime startTime;

    @Column(name = "TOKEN")
    private String token;

    @Column(name = "USERID")
    private long userId;

    @Column(name = "COMMENTS")
    private String comments;

    @Lob
    @Column(name = "SUMMARY")
    private String summary;

    @Column(name = "EXCUTIONTIME")
    private Long executionTime;

    @Lob
    @Column(name = "ERROR")
    private String error;

}
