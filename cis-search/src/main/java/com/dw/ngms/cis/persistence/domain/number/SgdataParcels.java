package com.dw.ngms.cis.persistence.domain.number;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "SGDATA_PARCELS")
public class SgdataParcels {

    @Id
    @Column(name = "RECORD_ID", insertable = false, updatable = false)
    private Long recordId;

    @Column(name = "SGNO")
    private String sgNo;

    @Column(name = "REGISTRATION_TOWNSHIP")
    private String registrationTownship;

    @Column(name = "FARMNAME")
    private String farmName;

    @Column(name = "REFERENCE_NUMBER")
    private String referenceNumber;

    @Column(name = "SCHEME_NAME")
    private String schemeName;

    @Column(name = "SCHEME_NUMBER")
    private String schemeNumber;

    @Column(name = "LOCAL_MUNICIPALITY_CODE")
    private String localMunicipalityCode;

    @Column(name = "LOCAL_MUNICIPALITY_NAME")
    private String localMunicipalityName;

    @Column(name = "PROVINCE")
    private String province;

    @Column(name = "REGION")
    private String region;

    @Column(name = "PARCEL")
    private String parcel;

    @Column(name = "PORTION")
    private String portion;

    @Column(name = "LPI")
    private String lpi;

    @Column(name = "PARCEL_TYPE")
    private String parcelType;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "DOCUMENT_TYPE_ID")
    private Long documentTypeId;

    @Column(name = "DOCUMENT_TYPE")
    private String documentType;

    @Column(name = "DOCUMENT_SUBTYPE_ID")
    private Long documentSubtypeId;

    @Column(name = "DOCUMENT_SUBTYPE")
    private String documentSubtype;


    @Column(name = "RECORD_TYPE_ID")
    private Long recordTypeId;

    @Column(name = "PROVINCE_ID")
    private Long provinceId;

    @Column(name = "REGISTRATION_TOWNSHIP_ID")
    private Long registrationTownshipId;

    @Column(name = "REGISTRATION_TOWNSHIP_NAME")
    private String registrationTownshipName;

    @Column(name = "LOCAL_MUNICIPALITY_ID")
    private Long localMunicipalityId;

    @Column(name = "REGION_ID")
    private Long regionId;

    @Column(name = "DESIGNAtION")
    private String designation;

    @Column(name = "REMAINDER_IND")
    private Long remainderInd;

}
