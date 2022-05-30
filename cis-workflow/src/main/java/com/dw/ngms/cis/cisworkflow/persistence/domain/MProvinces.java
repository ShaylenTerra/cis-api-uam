package com.dw.ngms.cis.cisworkflow.persistence.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "M_PROVINCES")
public class MProvinces {

    @Id
    @Column(name = "PROVINCEID")
    private Long provinceId;

    @Column(name = "ORGCODE")
    private String orgCode;

    @Column(name = "ORGNAME")
    private String orgName;

    @Column(name = "PROVINCECODE")
    private String provinceCode;

    @Column(name = "PROVINCENAME")
    private String provinceName;

    @Column(name = "PROVINCESHORTNAME")
    private String provinceShortName;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ISACTIVE")
    private String isActive;

    @Column(name = "CREATEDDATE")
    private LocalDate createdDate;


}
