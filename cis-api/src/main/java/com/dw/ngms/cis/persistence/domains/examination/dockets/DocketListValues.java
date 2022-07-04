package com.dw.ngms.cis.persistence.domains.examination.dockets;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "EXAM_DATASTORE")
public class DocketListValues {

    @Id
    @GeneratedValue(generator = "exam_d_list_item_value_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "exam_d_list_item_value_seq",sequenceName = "EXAM_DATASTORE_SEQ", allocationSize = 1)
    @Column(name="ID",insertable = false)
    private Long id;

    @Column(name="STRINGVALUE")
    private String stringvalue;

    @Column(name="EXAMINATIONID")
    private Long examinationid;

    @Column(name = "DOCKETTYPEID")
    private Long docketypeid;
}
