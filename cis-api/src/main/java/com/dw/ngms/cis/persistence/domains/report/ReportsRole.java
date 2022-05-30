package com.dw.ngms.cis.persistence.domains.report;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "M_REPORTS_ROLE")
public class ReportsRole {

    @Id
    @GeneratedValue(generator = "reports_role_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "reports_role_seq", sequenceName = "REPORT_ROLE_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "ROLE_ID")
    private Long roleId;

    @Column(name = "REPORT_ID")
    private Long reportId;

}
