package com.dw.ngms.cis.persistence.domains.fee;

import lombok.Data;

import javax.persistence.*;

/**
 * @author : prateekgoel
 * @since : 27/11/20, Fri
 **/
@Data
@Entity
@Table(name = "FEE_MASTER")
public class FeeMaster {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fee_master_seq")
    @SequenceGenerator(name = "fee_master_seq", sequenceName = "FEE_MASTER_SEQ", allocationSize = 1)
    @Column(name = "FEEID")
    private Long feeId;

    @Column(name = "FEE")
    private Long fee;

    @Column(name = "ISACTIVE")
    private Integer isActive;

    @Column(name = "SUBCATEGORYID")
    private Long feeSubCategoryId;

    @Column(name = "FEESCHEDULEID")
    private Long feeScaledId;

    @Column(name = "TYPEID")
    private Long feeTypeId;

}
