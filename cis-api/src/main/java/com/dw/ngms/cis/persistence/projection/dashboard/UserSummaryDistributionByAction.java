package com.dw.ngms.cis.persistence.projection.dashboard;

/**
 * @author : prateekgoel
 * @since : 15/03/21, Mon
 **/
public interface UserSummaryDistributionByAction {

    String getAction();

    Long getTotalTask();
}
