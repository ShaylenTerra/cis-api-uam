package com.dw.ngms.cis.persistence.domain.sectional;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "SGDATA_SECTIONAL")
@Deprecated
public class SgdataSectional {

    @Id
    @Column(name = "SGNO")
    private String sgno;

    @Column(name = "SCHEME_NAME")
    private String schemeName;

    @Column(name = "FILING_NUMBER")
    private String filingNumber;


    @Column(name = "DOCUMENT_NUMBER")
    private String documentNumber;

    @Column(name = "IMAGE_NUMBER")
    private String imageNumber;

    @Column(name = "SCHEME_NUMBER")
    private String schemeNumber;

    @Column(name = "PROV_CODE")
    private Long provCode;

    @Column(name = "PROVINCE", columnDefinition = "CHAR")
    private String province;

    @Column(name = "REGION")
    private String region;

    @Column(name = "PARCEL")
    private String parcel;

    @Column(name = "PORTION")
    private String portion;

    @Column(name = "LPI")
    private String lpi;

    @Column(name = "DOCUMENT_TYPE", columnDefinition = "CHAR")
    private String documentType;

    @Column(name = "DOCUMENT_SUBTYPE")
    private String documentSubtype;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "PAGENO")
    private Long pageno;

    @Column(name = "URL")
    private String url;

    @Column(name = "FTP_SITE_URL")
    private String ftpSiteUrl;

    @Column(name = "FILE_SIZE")
    private String fileSize;

    @Column(name = "PREVIEW")
    private String preview;

    @Column(name = "SCANDATE")
    private java.sql.Date scandate;

    @Column(name = "LOCAL_MUNICIPALITY_CODE")
    private String localMunicipalityCode;

    @Column(name = "LOCAL_MUNICIPALITY_NAME")
    private String localMunicipalityName;

    @Column(name = "FARM_ERF_NUMBER")
    private String farmErfNumber;

    @Column(name = "FARM_NAME")
    private String farmName;

    @Column(name = "MAJOR_MINOR_REGION")
    private String majorMinorRegion;

    @Column(name = "REGISTRATION_TOWNSHIP")
    private String registrationTownship;

    @Column(name = "PARCEL_TYPE")
    private String parcelType;

    @Column(name = "SECTIONAL_CODE")
    private Long sectionalCode;

}
