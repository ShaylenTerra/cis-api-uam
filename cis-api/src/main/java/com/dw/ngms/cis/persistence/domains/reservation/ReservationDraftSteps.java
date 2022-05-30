package com.dw.ngms.cis.persistence.domains.reservation;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "RES_DRAFT_STEPS")
public class ReservationDraftSteps {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "draft_id", nullable = false)
    private ReservationDraft reservationDraft;

    @Id
    @GeneratedValue(generator = "res_draft_steps_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "res_draft_steps_seq", sequenceName = "RES_DRAFT_STEPS_SEQ", allocationSize = 1)
    @Column(name = "STEP_ID")
    private Long stepId;

    @Column(name = "STEP_NO")
    private Long stepNo;

    @Transient
    private String reasonName;

    @Column(name = "REASON_ITEM_ID")
    private Long reasonItemId;

    @Column(name = "PARCEL_REQUESTED")
    private Long parcelRequested;

    @Column(name = "OTHER_DATA")
    private String otherData;

    @OneToMany(mappedBy = "reservationDraftSteps",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @OrderBy(value = "draftRequestId asc ")
    private Set<ReservationDraftRequest> reservationDraftRequests;

    @OneToMany(mappedBy = "reservationDraftSteps",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @OrderBy(value = "draftOutcomeId asc")
    private Set<ReservationDraftRequestOutcome> reservationDraftRequestOutcomes;

    @OneToMany(mappedBy = "reservationDraftSteps",
    fetch = FetchType.EAGER,
    cascade = CascadeType.ALL,
    orphanRemoval = true)
    @OrderBy(value = "OUTCOME_ID")
    private Set<ReservationOutcome> reservationOutcomes;
}
