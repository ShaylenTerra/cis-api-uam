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
@Table(name = "RESERVATION")
public class Reservation {
    @Id
    @Column(name = "WORKFLOW_ID")
    private Long workflowId;

    @Column(name = "DRAFT_ID")
    private Long draftId;

    @Column(name = "USERID")
    private Long userid;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PROVINCE_ID")
    private Long provinceId;

    @Column(name = "PURPOSE")
    private String purpose;

    @Column(name = "APPLICANT_USER_ID")
    private Long applicantUserId;

}
