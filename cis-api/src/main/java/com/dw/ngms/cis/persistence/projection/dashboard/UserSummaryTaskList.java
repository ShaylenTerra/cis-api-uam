package com.dw.ngms.cis.persistence.projection.dashboard;

import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;

/**
 * @author : prateekgoel
 * @since : 15/03/21, Mon
 **/
public interface UserSummaryTaskList {

    String getContext();

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    Timestamp getPostedOn();

    String getFullName();

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    Timestamp getActedOn();

    Long getProductivityMinutes();

    String getReferenceNo();

    String getProcessName();

    String getInternalStatusCaption();

    Long getWorkflowId();

}
