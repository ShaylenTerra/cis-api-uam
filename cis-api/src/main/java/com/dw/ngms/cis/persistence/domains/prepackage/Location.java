package com.dw.ngms.cis.persistence.domains.prepackage;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "LOCATION")
public class Location {

    @Id
    @Column(name = "BOUNDARYID", updatable = false, insertable = false)
    private Long boundaryId;

    @Column(name = "CAPTION")
    private String caption;

    @Column(name = "MDBCODE")
    private String mdbcode;

    @Column(name = "CATEGORY")
    private String category;

    @Column(name = "PARENTBOUNDARYID")
    private Long parentBoundaryId;

    @Column(name = "AREASQKM")
    private Long areasqkm;

    @Column(name = "ALIASCAPTION")
    private String aliascaption;

    @Column(name = "OFFICECODE")
    private String officeCode;

    @Column(name = "RESERVATIONSYSTEM")
    private String reservationSystem;
}
