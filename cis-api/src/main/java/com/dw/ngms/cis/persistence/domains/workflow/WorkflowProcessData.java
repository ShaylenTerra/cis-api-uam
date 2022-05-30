package com.dw.ngms.cis.persistence.domains.workflow;

import com.dw.ngms.cis.persistence.domains.cart.RequesterInformation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author : prateekgoel
 * @since : 20/06/21, Sun
 **/
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WorkflowProcessData {

    private RequesterInformation requesterInformation;

    private NotifyManagerData notifyManagerData;

    private QueryData queryData;
}
