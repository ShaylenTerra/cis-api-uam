package com.dw.ngms.cis.persistence.projection.ngi;

public interface NgiSearchDataProjection {

    Long getRecordId();

    String getCategoryName();

    String getName();

    String getDescription();

    String getDocumentType();

    String getDocumentSubType();

    String getDocumentTypeId();

    String getDocumentSubTypeId();

}
