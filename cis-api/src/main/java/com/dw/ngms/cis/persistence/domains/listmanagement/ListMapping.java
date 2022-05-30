package com.dw.ngms.cis.persistence.domains.listmanagement;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "M_LIST_MAPPING")
public class ListMapping {

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "list_mapping", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "list_mapping", sequenceName = "M_LIST_MAPPING_SEQ", allocationSize = 1)
    private Long id;

    @Column(name = "PARENT_LISTCODE")
    private Long parentListCode;

    @Column(name = "PARENT_ITEMID")
    private Long parentItemId;

    @Column(name = "CHILD_ITEMID")
    private Long childItemId;

    @Column(name = "CHILD_LISTCODE")
    private Long childListCode;

    @Column(name = "ISACTIVE")
    private Long isActive;


}
