package com.dw.ngms.cis.persistence.domains.report;

import com.dw.ngms.cis.persistence.domains.Roles;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@Entity
@Table(name = "M_REPORTS")
public class Reports {

    @Id
    @GeneratedValue(generator = "reports_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "reports_seq", sequenceName = "REPORTS_SEQ", allocationSize = 1)
    @Column(name = "REPORT_ID")
    private Long reportId;

    @Column(name = "REPORT_NAME")
    private String reportName;

    @Column(name = "MODULE_ID")
    private Long moduleId;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate;

    @Column(name = "UPDATED_DATE")
    private LocalDateTime updatedDate;

    @OneToMany(targetEntity = Roles.class,
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    private Collection<ReportsRole> roles = new ArrayList<>();

}
