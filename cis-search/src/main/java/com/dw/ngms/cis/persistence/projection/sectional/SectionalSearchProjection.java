package com.dw.ngms.cis.persistence.projection.sectional;

/**
 * @author : prateekgoel
 * @since : 10/01/21, Sun
 **/
public interface SectionalSearchProjection {

    String getDeedRegistrationNumber();

    String getFilingNumber();

    String getSgNo();

    Long getRecordId();

    String getSchemeName();

    String getProvinceId();

    String getProvince();

    String getLocalMunicipalityCode();

    String getLocalMunicipalityName();

    String getParcelType();

    String getDocumentType();

    Long getDocumentTypeId();

    Long getDocumentSubTypeId();

    String getDocumentSubType();

    String getRegion();

    String getParcel();

    String getPortion();

    String getLpi();


}
