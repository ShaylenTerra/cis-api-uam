package com.dw.ngms.cis.persistence.domains.listmanagement;

import lombok.Data;

import javax.persistence.*;

/**
 * @author : prateekgoel
 * @since : 06/12/20, Sun
 **/
@Entity
@Data
@Table(name = "M_LIST_MASTER")
public class ListMaster {

    @Id
    @GeneratedValue(generator = "m_list_master_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "m_list_master_seq", sequenceName = "M_LIST_MASTER_SEQ", allocationSize = 1)
    @Column(name = "LISTCODE")
    private Long listCode;

    @Column(name = "CAPTION")
    private String caption;

    @Column(name = "ISSTATIC")
    private Long isStatic;

}
