package com.dw.ngms.cis.persistence.domains.workflow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author : prateekgoel
 * @since : 23/06/21; Wed
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AutoDispatchWorkflow {

    private BigDecimal processId;
    private String processName;
    private String actionRequiredDescription;
    private BigDecimal workflowId;
    private String referenceNo;
    private BigDecimal provinceId;
    private BigDecimal turnaroundDuration;
    private BigDecimal actionId;
    private BigDecimal actionRequired;
    private Date allocatedOn;
    private String assignedToUser;
    private Date diariseDate;
    private BigDecimal internalStatus;
    private String internalStatusCaption;
    private BigDecimal externalStatus;
    private String externalStatusCaption;
    private BigDecimal priorityFlag;
    private String priorityName;
    private BigDecimal escalationTime;
    private String actionContext;
    private Date triggeredOn;
    private BigDecimal nodeId;
    private BigDecimal parentProcessId;

}
