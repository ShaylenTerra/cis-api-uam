package com.dw.ngms.cis.persistence.domains.lodgement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "LDG_DRAFT_REQUEST")
public class LodgementDraftRequest {

    @Id
    @GeneratedValue(generator = "ldg_draft_request_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "ldg_draft_request_seq",sequenceName = "ldg_draft_request_seq", allocationSize = 1)
    @Column(name = "REQUESTID")
    private Long requestId;

    @Column(name = "OUTCOME_ID_RES")
    private Long outcomeIdReservation;

    @Column(name = "DESIGNATION")
    private String designation;

    @Column(name = "LPI")
    private String lpi;

    @Column(name = "LOCATION_ID")
    private Long locationId;

    @Column(name = "PARENT_PARCELS")
    private String parentParcels;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "STEP_ID", nullable = false)
    private LodgementDraftStep lodgementDraftStep;

    @OneToMany(mappedBy = "lodgementDraftRequest",
            fetch=FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @OrderBy(value = "documentId ASC")
    private Set<LodgementDraftDocument> lodgementDraftDocuments;

    @Column(name = "RES_WORKFLOW_ID")
    private Long reservationWorkflowId;

}
