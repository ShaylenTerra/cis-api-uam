package com.dw.ngms.cis.persistence.domains.examination;

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
@Table(name = "EXAMINATION")
public class Examination {

    @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)

    @GeneratedValue(generator = "exm_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "exm_seq",sequenceName = "exm_seq", allocationSize = 1)
    @Column(name = "EXAM_ID",insertable= false)
    private Long examId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Column(name = "LODGEMENT_ID")
    private Long lodgementId;

    @Column(name = "ALLOCATED_USER_ID")
    private Long allocatedUserId;

    @Column(name = "WORKFLOW_ID", nullable = false)
    private Long workflowId;

    @Column(name = "STATUS_ID")
    private Long statusId;


}
