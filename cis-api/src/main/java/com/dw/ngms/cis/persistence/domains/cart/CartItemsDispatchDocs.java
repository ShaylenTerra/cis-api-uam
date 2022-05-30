package com.dw.ngms.cis.persistence.domains.cart;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "CART_ITEMS_DISPATCH_DOCS")
public class CartItemsDispatchDocs {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cart_items_dispatch_docs_seq")
    @SequenceGenerator(name = "cart_items_dispatch_docs_seq", sequenceName = "CART_ITEMS_DISPATCH_DOCS_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "WORKFLOW_ID")
    private Long workflowId;

    @Column(name = "CART_ITEMS_ID")
    private Long cartItemsId;

    @Column(name = "DOCUMENT_ID")
    private Long documentId;

    @Column(name = "NOTES")
    private String notes;
}
