package com.dw.ngms.cis.persistence.projection.fee;

/**
 * @author : prateekgoel
 * @since : 26/01/21, Tue
 **/
public interface InvoiceItemCostProjection {

    String getCategoryName();

    String getSubCategoryName();

    String getSubCategoryDescription();

    Double getFee();

    String getType();

}
