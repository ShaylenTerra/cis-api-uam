package com.dw.ngms.cis.persistence.domains.fee;

import lombok.Data;

import javax.persistence.*;

/**
 * @author : prateekgoel
 * @since : 27/11/20, Fri
 **/
@Data
@Entity
@Table(name = "FEE_SUBCATEGORY")
public class FeeSubCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fee_subcategory_seq")
    @SequenceGenerator(name = "fee_subcategory_seq", sequenceName = "FEE_SUBCATEGORY_SEQ", allocationSize = 1)
    @Column(name = "SUBCATID")
    private Long feeSubCategoryId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CATEGORYID")
    private Long categoryId;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ISACTIVE")
    private Integer isActive;


}
