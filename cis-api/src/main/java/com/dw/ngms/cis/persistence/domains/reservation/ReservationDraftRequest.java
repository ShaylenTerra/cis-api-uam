package com.dw.ngms.cis.persistence.domains.reservation;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "RES_DRAFT_REQUEST")
public class ReservationDraftRequest {

    @Id
    @GeneratedValue(generator = "res_draft_request", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "res_draft_request", sequenceName = "RES_DRAFT_REQUEST_SEQ",allocationSize = 1)
    @Column(name = "DRAFT_REQUEST_ID")
    private Long draftRequestId;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "step_id", nullable = false)
    private ReservationDraftSteps reservationDraftSteps;

    @Column(name = "RECORD_ID")
    private Long recordId;

    @Column(name = "LOCATION_ID")
    private Long locationId;

    @Column(name = "LPI")
    private String lpi;

    @Column(name = "DESIGNATION")
    private String designation;

    @Column(name = "PARCEL")
    private String parcel;

    @Column(name = "PORTION")
    private String portion;

    @Column(name = "DRAFT_OUTCOME_ID")
    private Long draftOutcomeId;

    @Column(name = "RECORD_TYPE_ID")
    private Long recordTypeId;

    @Column(name = "LOCATION")
    private String location;

}
