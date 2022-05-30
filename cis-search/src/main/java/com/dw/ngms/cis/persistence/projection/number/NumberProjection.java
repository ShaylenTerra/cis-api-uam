package com.dw.ngms.cis.persistence.projection.number;

/**
 * @author : prateekgoel
 * @since : 07/01/21, Thu
 **/
public interface NumberProjection {


    String getSgNo();

    Long getRecordId();

    Long getProvinceId();

    String getProvince();

    String getLocalMunicipalityCode();

    String getLocalMunicipalityName();

    String getDocumentType();

    Long getDocumentTypeId();

    String getDocumentSubType();

    Long getDocumentSubTypeId();

    String getRegion();

    String getParcel();

    String getPortion();

    String getLpi();

    String getParcelType();

    String getDescription();

}
