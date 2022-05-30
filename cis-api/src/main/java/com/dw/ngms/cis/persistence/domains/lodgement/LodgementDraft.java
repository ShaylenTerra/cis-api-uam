package com.dw.ngms.cis.persistence.domains.lodgement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "LDG_DRAFT")
public class LodgementDraft {

    @Id
    @GeneratedValue(generator = "ldg_draft_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "ldg_draft_seq",sequenceName = "ldg_draft_seq", allocationSize = 1)
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

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "UPDATED_ON")
    private LocalDateTime updatedOn;

    @Column(name = "APPLICANT")
    private Long applicant;

    @Column(name = "APPLICANT_USERID")
    private Long applicantUserId;

    @Column(name = "DELIVERY_METHOD")
    private String deliveryMethod;

    @Column(name = "WORKFLOW_ID")
    private Long workflowId;

    @Column(name = "ISACTIVE")
    private Long isActive;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "IS_PRIMARY_EMAIL")
    private Long isPrimaryEmail;

    @Column(name = "DELIVERY_METHOD_ITEM_ID")
    private Long deliveryMethodItemId;

    @Column(name = "PROCESSID")
    private Long processId;

    @Column(name = "FILE_REF")
    private String fileRef;

    @OneToMany(mappedBy = "lodgementDraft",
            fetch=FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @OrderBy(value = "annexureId ASC")
    private Set<LodgementDraftAnnexure> lodgementDraftAnnexures;

    @OneToMany(mappedBy = "lodgementDraft",
            fetch=FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @OrderBy(value = "stepId ASC")
    private Set<LodgementDraftStep> lodgementDraftStep;

    @OneToMany(mappedBy = "lodgementDraft",
            fetch=FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @OrderBy(value = "payId ASC")
    private Set<LodgementDraftPayment> lodgementDraftPayments;


}
