package com.dw.ngms.cis.persistence.domains.reservation;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "RES_DRAFT")
public class ReservationDraft {

    @Id
    @GeneratedValue(generator = "res_draft_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "res_draft_seq", sequenceName = "RES_DRAFT_SEQ", allocationSize = 1)
    @Column(name = "DRAFT_ID")
    private Long draftId;

    @Column(name = "USERID")
    private Long userId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PROVINCE_ID")
    private Long provinceId;

    @Column(name = "DATED")
    private LocalDateTime dated;

    @Column(name = "PURPOSE")
    private String purpose;

    @Column(name = "UPDATED_ON")
    private LocalDateTime updatedOn;

    @Column(name = "APPLICANT")
    private Long applicant;

    @Column(name = "APPLICANT_USERID")
    private Long applicantUserId;

    @Column(name = "SURVEY_DATE")
    private LocalDateTime surveyDate;

    @Column(name = "DELIVERY_METHOD")
    private String deliveryMethod;

    @Column(name = "DELIVERY_METHOD_ITEM_ID")
    private Long deliveryMethodItemId;

    @Column(name = "WORKFLOW_ID")
    private Long workflowId;

    @Column(name = "ISACTIVE")
    private Long isActive;

    @Column(name = "IS_PRIMARY_EMAIL")
    private Long isPrimaryEmail;

    @Column(name = "EMAIL")
    private String email;

    @OneToMany(mappedBy = "reservationDraft",
            fetch=FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @OrderBy(value = "documentId ASC")
    private Set<ReservationDraftDocument> reservationDraftDocuments;

    @OneToMany(mappedBy = "reservationDraft",
            fetch=FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @OrderBy(value = "stepId ASC")
    private Set<ReservationDraftSteps> reservationDraftSteps;

    @Column(name = "PROCESSID")
    private Long processId;

    @Column(name = "TO_USERID")
    private Long toUserId;

    @Column(name = "FILE_REF")
    private String fileRef;
}
