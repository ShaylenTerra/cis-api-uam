package com.dw.ngms.cis.persistence.projection.sectional;

/**
 * @author : prateekgoel
 * @since : 10/01/21, Sun
 **/
public interface SectionalSearchErfProjection extends SectionalSearchProjection {

    String getSchemeNumber();

    String getEfNumber();

    String getTownshipAllotmentName();

}
