package com.dw.ngms.cis.persistence.domains.report;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "WORKFLOW_PRODUCTIVITY")
public class WorkflowProductivity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "workflow_productivity_sequence")
    @SequenceGenerator(name = "workflow_productivity_sequence", sequenceName = "WORKFLOW_PRODUCTIVITY_SEQUENCE",
            allocationSize = 1)
    private Long id;

    @Column(name = "ACTION_ID")
    private Long actionId;

    @Column(name = "PRODUCTIVITY_DATE")
    private LocalDateTime productivityDate;

    @Column(name = "PRODUCTIVITY_MINUTES")
    private Long productivityMinutes;

    @Column(name = "CONTEXT")
    private String context;

}
