package com.dw.ngms.cis.persistence.domains.reservation;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "RES_DRAFT_REQUEST_OUTCOME")
public class ReservationDraftRequestOutcome {

    @Id
    @GeneratedValue(generator = "res_draft_request_outcome_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "res_draft_request_outcome_seq",
            sequenceName = "RES_DRAFT_REQUEST_OUTCOME_SEQ",
            allocationSize = 1)
    @Column(name = "DRAFT_OUTCOME_ID")
    private Long draftOutcomeId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "step_id", nullable = false)
    private ReservationDraftSteps reservationDraftSteps;

    @Column(name = "DESIGNATION")
    private String designation;

    @Column(name = "PARCEL")
    private String parcel;

    @Column(name = "PORTION")
    private String portion;

    @Column(name = "LOCATION_ID")
    private Long locationId;

    @Column(name = "LOCATION")
    private String location;

    @Column(name = "RECORD_TYPE_ID")
    private Long recordTypeId;

    @Column(name = "LPI")
    private String lpi;

}
