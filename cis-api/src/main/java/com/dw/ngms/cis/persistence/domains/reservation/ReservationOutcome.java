package com.dw.ngms.cis.persistence.domains.reservation;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "RESERVATION_OUTCOME")
public class ReservationOutcome {

    @Id
    @GeneratedValue(generator = "reservation_outcome_seq",strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "reservation_outcome_seq", sequenceName = "RESERVATION_OUTCOME_SEQ", allocationSize = 1)
    @Column(name = "OUTCOME_ID")
    private Long outcomeId;

    @Column(name = "WORKFLOW_ID")
    private Long workflowId;

    @Column(name = "DRAFT_ID")
    private Long draftId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "step_id", nullable = false)
    private ReservationDraftSteps reservationDraftSteps;


    @Column(name = "REQUEST_ID")
    private Long requestId;

    @Column(name = "LOCATION_ID")
    private Long locationId;

    @Column(name = "LOCATION_NAME")
    private String locationName;

    @Column(name = "REASON_ITEM_ID")
    private Long reasonItemId;

    @Column(name = "PARCEL")
    private Long parcel;

    @Column(name = "PORTION")
    private Long portion;

    @Column(name = "LPI")
    private String lpi;

    @Column(name = "DESIGNATION")
    private String designation;

    @Column(name = "STATUS_ITEM_ID")
    private Long statusItemId;

    @Column(name = "OWNER_USERID")
    private Long ownerUserId;

    @Column(name = "ISSUE_DATE")
    private LocalDateTime issueDate;

    @Column(name = "EXPIRY_DATE")
    private LocalDateTime expiryDate;

    @Column(name = "ALGORITHM")
    private String algorithm;

}
