package com.dw.ngms.cis.cisworkflow.persistence.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "M_LIST_ITEM")
public class MListItem {

    @Id
    @Column(name = "ITEMID")
    private Long itemId;

    @Column(name = "ITEMCODE")
    private String itemCode;

    @Column(name = "LISTCODE")
    private Long listCode;

    @Column(name = "CAPTION")
    private String caption;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ISACTIVE")
    private Long isActive;

}
