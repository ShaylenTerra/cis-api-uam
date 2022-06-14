package com.dw.ngms.cis.persistence.domains.examination.dockets;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "EXAM_MASTERDOCKET")
public class MasterDocket {

    @Id
    @GeneratedValue(generator = "masterdocketseq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "masterdocketseq",sequenceName = "masterdocketseq", allocationSize = 1)
    @Column(name = "MASTERID",insertable= false)
    private Long masterId;

    @Column(name = "BATCH_NO")
    private String batchNo;

    @Column(name = "SG_NO")
    private String sgNo;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "SR_NO")
    private String srNo;

    @Column(name = "DATE_RECIEVED")
    private LocalDateTime dateReceived;

    @Column(name = "NO_OF_DGMS")
    private Long NoOfDgms;

    @Column(name = "PLANSIZEACCEPPTABLE")
    private Long planSizeAcceptable;

    @Column(name = "LANDSURVEYOR")
    private String landSurveyor;

    @Column(name = "COMPANYNAME")
    private String companyName;

    @Column(name = "SUBMITTEDBY")
    private String submittedBy;

    @Column(name = "RETURNTO")
    private String returnTo;

    @Column(name = "REFNUMBER")
    private String refNumber;

    @Column(name = "FILEREFERENCE")
    private String fileReference;

    @Column(name = "RECEIVEDBY")
    private String receivedBy;

    @Column(name = "EXAMINATIONFEES")
    private Long examinationFees;

    @Column(name = "CHECKEDBY")
    private String checkedBy;

    @Column(name = "RECIEPTNO")
    private String recieptNo;

    @Column(name = "DOCKETTYPEITEMID")
    private Long docketTypeItemId;

    @Column(name = "DOCKETSUBTYPEID")
    private Long docketSubTypeId;

}
