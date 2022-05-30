package com.dw.ngms.cis.persistence.domains.workflow;

import com.dw.ngms.cis.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @author : prateekgoel
 * @since : 28/12/20, Mon
 **/
@Entity
@Table(name = "TRANSACTIONS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transactions {

    @Id
    @GeneratedValue(generator = "transactions_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "transactions_seq", sequenceName = "TRANSACTIONS_SEQ", allocationSize = 1)
    @Column(name = "TRANSACTIONID")
    public Long transactionId;

    @Column(name = "TTYPE")
    public TransactionType ttype;

    @Column(name = "START_TIME")
    public Date startTime;

    @Column(name = "TOKEN")
    public String token;

    @Column(name = "USERID")
    public Long userId;

    @Column(name = "COMMENTS")
    public String comments;

    @Column(name = "SUMMARY")
    public String summary;

    @Column(name = "EXCUTIONTIME")
    public Long executionTime;

    @Column(name = "ERROR")
    public String error;

}
