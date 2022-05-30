package com.dw.ngms.cis.persistence.domains.fee;

import lombok.Data;

import javax.persistence.*;

/**
 * @author : prateekgoel
 * @since : 27/11/20, Fri
 **/
@Data
@Entity
@Table(name = "FEE_TYPE")
public class FeeType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "fee_type_seq")
    @SequenceGenerator(name = "fee_type_seq", sequenceName = "FEE_TYPE_SEQ", allocationSize = 1)
    @Column(name = "TYPEID")
    private Long typeId;

    @Column(name = "TYPE")
    private String feeType;

    @Column(name = "DESCRIPTIPN")
    private String description;

    @Column(name = "ISACTIVE")
    private Integer isActive;

    @Column(name = "TEXTBOXTYPE")
    private Long textBoxType;

}
