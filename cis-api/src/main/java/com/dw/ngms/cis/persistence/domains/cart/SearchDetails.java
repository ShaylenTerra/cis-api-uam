package com.dw.ngms.cis.persistence.domains.cart;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author : prateekgoel
 * @since : 10/02/21, Wed
 **/
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchDetails {

    private String description;

    private String documentType;

    private Long documentTypeId;

    private String documentSubType;

    private Long documentSubTypeId;

    private String localMunicipalityCode;

    private String localMunicipalityName;

    private String lpi;

    private Long recordId;

    private String parcel;

    private String parcelType;

    private String portion;

    private String province;

    private Long provinceId;

    private String region;

    private String sgNo;

    private String erfNumber;

    private String farmNumber;

    private String farmName;

    private String holdingNumber;

    private String leaseNo;

    private String compilationNo;

    private String deedNo;

    private String filingNo;

    private String surveyRecordNo;

    private String townshipAllotmentName;

    private String administrativeDistrict;

    private String name;

    private String categoryName;
}
