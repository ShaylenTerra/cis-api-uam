package com.dw.ngms.cis.persistence.projection.workflow;

/**
 * @author : pragayanshu
 * @since : 2022/03/28, Mon
 **/
public interface WorkflowUserFeedbackProjection {

    String getYear();
    String getStatus();
    Long getNoOfRequests();

}
