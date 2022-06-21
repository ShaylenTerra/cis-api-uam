package com.dw.ngms.cis.persistence.domains.examination.dockets;

/**
 * @author Shaylen Budhu on 08-06-2022
 */

import io.micrometer.core.annotation.Counted;
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
@Table(name = "EXAM_DIAGRAM_DOCKET")
public class DiagramDocket {

    @Id
    @GeneratedValue(generator = "exam_diagram_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "exam_diagram_seq",sequenceName = "EXAM_DIAGR_DOCKET", allocationSize = 1)
    @Column(name = "DIAGRAMID",updatable = false,insertable = false)
    private Long diagramId;

    @Column(name = "EXAMINERNAME")
    private String examinerName;

    @Column(name = "SCRUTINIZERNAME")
    private String scrutinizerName;

    @Column(name = "PA_NAME")
    private String paName;

    @Column(name = "EXAMINERCHECKLIST")
    private String examinerChecklist;

    @Column(name = "SCRUTINIZERCHECKLIST")
    private String scrutinizerChecklist;

    @Column(name = "PA_CHECKLIST")
    private String paCheckList;

    @Column(name = "DESIGNATION_NUMBERING")
    private String designationNumbering;

    @Column(name = "COMPUTING")
    private String computing;

    @Column(name = "REGISTRY_DISPATCH")
    private String registryDispatch;

    @Column(name = "POSTAPPROVAL")
    private String postApproval;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "EXAMID")
    private Long examinationId;

}
