package com.dw.ngms.cis.persistence.domains.examination.dockets;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "EXAM_D_List_Item")
public class DocketList {

    @Id
    @GeneratedValue(generator = "exam_d_list_item_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "exam_d_list_item_seq",sequenceName = "EXAM_D_LIST_ITEM_SEQ", allocationSize = 1)
    @Column(name = "DOC_LIST_ID")
    private Long docketListId;

    @Column(name = "ITEMCODE")
    private String itemCode;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PARENT_ID")
    private Long parentId;

    @Column(name = "DOCKET_TYPE_ID")
    private Long docketTypeId;

    @Column(name = "STATUS")
    private char status;

    @Column(name = "VALUE")
    private String value;

}
