package com.dw.ngms.cis.cisworkflow.persistence.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "M_OFFICE_TIMINGS")
public class MOfficeTimings {

    @Id
    @Column(name = "TIMINGID")
    private Long timingId;

    @Column(name = "PROVINCEID")
    private Long provinceId;

    @Column(name = "FROMTIME")
    private LocalDateTime fromTime;

    @Column(name = "TOTIME")
    private LocalDateTime toTime;

    @Column(name = "EFFECTIVEFROM")
    private LocalDateTime effectiveFrom;

    @Column(name = "STATUS")
    private Long status;

    @Column(name = "FROMDATE")
    private LocalDateTime fromDate;

    @Column(name = "TODATE")
    private LocalDateTime toDate;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "OFFICETIMINGTYPE")
    private String officeTimingType;

    @Column(name = "USERID")
    private Long userid;


}
