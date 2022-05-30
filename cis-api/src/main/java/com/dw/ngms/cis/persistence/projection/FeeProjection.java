package com.dw.ngms.cis.persistence.projection;

/**
 * @author : prateekgoel
 * @since : 04/12/20, Fri
 **/
public interface FeeProjection {

    Long getFeeScaleId();

    Long getFeeTypeId();

    String getFeeScaleName();

    String getFeeTypeName();

    String getFee();

    Long getFeeSubCategoryId();

    String getFeeSubCategoryName();

    String getFeeSubCategoryDescription();

    String getFeeCategoryName();

    String getFeeCategoryDescription();

    Long getFeeCategoryId();

    Long getFeeMasterIsActive();

    Long getFeeMasterId();

}
