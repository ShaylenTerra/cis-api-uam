package com.dw.ngms.cis.persistence.projection.prepackage;

import java.util.Date;

/**
 * @author : prateekgoel
 * @since : 21/01/21, Thu
 **/
public interface PrepackageSubscriptionProjection {

    String getSubscriptionStatus();

    Date getSubscriptionDate();

    String getReferenceNo();

    String getDescription();

    String getType();

    String getFrequency();

    String getLocation();

    String getSubscriptionName();

    Long getSubscriptionId();

    String getLocationName();


}
