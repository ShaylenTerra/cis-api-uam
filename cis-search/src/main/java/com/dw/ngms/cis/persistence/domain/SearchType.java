package com.dw.ngms.cis.persistence.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author : prateekgoel
 * @since : 07/12/20, Mon
 **/
@Entity
@Getter
@Setter
@Table(name = "SEARCH_TYPE")
public class SearchType {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "search_type_seq")
    @SequenceGenerator(name = "search_type_seq", sequenceName = "SEARCH_TYPE_SEQ", allocationSize = 1)
    @Column(name = "SEARCHTYPEID")
    private Long searchTypeId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "TAG")
    private String tag;

    @Column(name = "PARENTSEARCHTYPEID")
    private Long parentSearchTypeId;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CONTROLTYPE")
    private String controlType;

    @Column(name = "ISACTIVE")
    private Long isActive;

    @Column(name = "CONFIG")
    private String config;

    @Column(name = "SORTID")
    private Float sortId;

    @Column(name = "TEMPLATE_LIST_ITEMID")
    private Long templateListItemId;

    @Column(name = "CART_EDITABLE")
    private String cartEditable;

}
