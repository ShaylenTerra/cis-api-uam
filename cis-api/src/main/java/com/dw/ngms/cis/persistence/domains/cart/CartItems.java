package com.dw.ngms.cis.persistence.domains.cart;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CART_ITEMS")
public class CartItems {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_item_seq")
    @SequenceGenerator(name = "cart_item_seq", sequenceName = "CART_ITEMS_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long cartItemId;

    @Column(name = "CART_DATA_ID")
    private Long cartDataId;

    @Column(name = "WORKFLOWID")
    private Long workflowId;

    @Column(name = "LPICODE")
    private String lpicode;

    @Column(name = "SNO")
    private String sno;

    @Column(name = "ITEM")
    private String item;

    @Column(name = "DETAILS")
    private String details;

    @Column(name = "FINALCOST")
    private Double finalCost = 0.0D;

    @Column(name = "SYSTEM_ESTIMATE")
    private Double systemEstimate = 0.0D;

    @Column(name = "TIME_REQUIRED")
    private String timeRequired;

    @Column(name = "COMMENTS")
    private String comments;

    @Column(name = "TYPE")
    private String type;

    @Column(name = "DISPATCH_COMMENT")
    private String dispatchComment;

    @Column(name = "DISPATCH_STATUS")
    private Integer dispatchStatus;

    @Column(name = "NOTES")
    private String notes;
}
