package com.dw.ngms.cis.persistence.domains.reservation;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "RESERVATION_CONDITION")
public class ReservationCondition {

    @Id
    @SequenceGenerator(name = "reservation_condition_seq", sequenceName = "RESERVATION_CONDITION_SEQ", allocationSize = 1)
    @GeneratedValue(generator = "reservation_condition_seq", strategy = GenerationType.SEQUENCE)
    @Column(name = "CONDITION_ID")
    private Long conditionId;

    @Column(name = "DRAFT_ID")
    private Long draftId;

    @Column(name = "STEP_ID")
    private Long stepId;

    @Column(name = "CONDITION")
    private String condition;

    @Column(name = "CONDITION_APLH")
    private String conditionAlphabet;

    @Column(name = "RESERVATION_REASON")
    private String reason;

    @Column(name = "STEP_NO")
    private Long stepNo;

}
