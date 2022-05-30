package com.dw.ngms.cis.persistence.projection.parcel;

/**
 * @author : prateekgoel
 * @since : 10/05/21, Mon
 **/
public interface ParcelProjection {

    String getSgNo();

    Long getRecordId();

    String getTownshipName();

    String getProvinceId();

    String getProvince();

    String getLocalMunicipalityCode();

    String getLocalMunicipalityName();

    String getDocumentType();

    Long getDocumentTypeId();

    String getDocumentSubType();

    Long getDocumentSubTypeId();

    String getParcelType();

    String getRegion();

    String getPortion();

    String getLpi();

    String getDescription();

}
