package com.dw.ngms.cis.persistence.projection.sectional;

/**
 * @author : prateekgoel
 * @since : 10/01/21, Sun
 **/
public interface SectionalSearchFarmProjection extends SectionalSearchProjection {

    String getFarmName();

    String getAdministrativeDistrict();

    String getFarmNumber();


}
