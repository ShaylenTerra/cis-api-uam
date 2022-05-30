package com.dw.ngms.cis.persistence.domains.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CART_DISPATCH")
public class CartDispatch {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_dispatch_seq")
    @SequenceGenerator(name = "cart_dispatch_seq", sequenceName = "CART_DISPATCH_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "WORKFLOW_ID")
    private Long workflowId;

    @Column(name = "TRANSACTION_ID")
    private Long transactionId;

    @Column(name = "DISPATCH_DETAILS")
    private String dispatchDetails;

}
