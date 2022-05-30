package com.dw.ngms.cis.persistence.domains.reservation;

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
@Table(name = "RES_DRAFT_TRANSFER")
public class ReservationDraftTransfer {

    @Id
    @GeneratedValue(generator = "res_draft_transfer_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "res_draft_transfer_seq", sequenceName = "RES_DRAFT_TRANSFER_SEQ", allocationSize = 1)
    @Column(name = "TRANSFERID")
    private Long transferId;

    @Column(name = "OUTCOME_ID")
    private Long outcomeId;

    @Column(name = "DRAFTID")
    private Long draftId;


}
