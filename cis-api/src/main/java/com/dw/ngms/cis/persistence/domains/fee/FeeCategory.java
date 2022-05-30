package com.dw.ngms.cis.persistence.domains.fee;

import lombok.Data;

import javax.persistence.*;

/**
 * @author : prateekgoel
 * @since : 27/11/20, Fri
 **/
@Data
@Entity
@Table(name = "FEE_CATEGORY")
public class FeeCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fee_category_seq")
    @SequenceGenerator(name = "fee_category_seq", sequenceName = "FEE_CATEGORY_SEQ", allocationSize = 1)
    @Column(name = "CATID")
    private Long feeCategoryId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ISACTIVE")
    private Integer isActive;

}
