package com.dw.ngms.cis.persistence.domain.parcel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SGDATA_NUMBER_SEARCH")
public class SgdataNumberSearch {

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "REGISTRATION_TOWNSHIP")
    private String registrationTownship;

    @Column(name = "FARMNAME")
    private String farmname;

    @Column(name = "PROVINCE")
    private String province;

    @Column(name = "SGNO")
    private String sgno;

    @Column(name = "DOCUMENTNO")
    private String documentno;

    @Column(name = "NUMBERS")
    private String numbers;

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

    @Column(name = "DOCUMENT_TYPE")
    private String documentType;

    @Column(name = "DOCUMENT_SUBTYPE")
    private String documentSubtype;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PAGENO")
    private Long pageno;

    @Column(name = "SCANDATE")
    private java.sql.Date scandate;

    @Column(name = "URL")
    private String url;

    @Column(name = "FTP_SITE_URL")
    private String ftpSiteUrl;

    @Column(name = "PREVIEW")
    private String preview;

    @Column(name = "LOCAL_MUNICIPALITY_CODE")
    private String localMunicipalityCode;

    @Column(name = "LOCAL_MUNICIPALITY_NAME")
    private String localMunicipalityName;

    @Column(name = "FILE_SIZE")
    private String fileSize;

    @Column(name = "NUMBERS_CODE")
    private Long numbersCode;

    @Column(name = "PROV_CODE")
    private Long provCode;

    @Column(name = "DOCUMENT_TYPE_ID")
    private Long documentTypeId;

    @Column(name = "DOCUMENT_SUBTYPE_ID")
    private Long documentSubtypeId;

    @Column(name = "THUMBNAIL")
    private String thumbnail;

}

