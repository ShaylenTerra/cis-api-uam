package com.dw.ngms.cis.persistence.domains.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USER_TRANSACTION")
public class UserTransaction {
    @Id
    @Column(name = "USERTRANSACTIONID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_transaction_id_sequence")
    @SequenceGenerator(name = "user_transaction_id_sequence", sequenceName = "USER_TRANSACTION_SEQ", allocationSize = 1)
    private Long userTransactionId;

    @Column(name = "CONTEXT")
    private String context;

    @Column(name = "STATUSITEMID")
    private Long statusItemId;

    @Column(name = "USERID")
    private Long userId;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "CREATEDDATE")
    private LocalDateTime createdDate;


}
