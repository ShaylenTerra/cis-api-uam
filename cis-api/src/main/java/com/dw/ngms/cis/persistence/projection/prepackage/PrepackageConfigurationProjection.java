package com.dw.ngms.cis.persistence.projection.prepackage;

import com.fasterxml.jackson.annotation.JsonRawValue;

/**
 * @author : prateekgoel
 * @since : 21/01/21, Thu
 **/
public interface PrepackageConfigurationProjection {

    String getSampleFileName();

    String getDescription();

    String getName();

    Long getPrePackageId();

    String getPrepackageDataType();

    String getPrepackageDataTypeId();

    @JsonRawValue
    String getConfigurationData();

    Long getCost();

    String getActive();

    String getFolder();

}
