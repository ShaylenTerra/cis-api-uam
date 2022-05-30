package com.dw.ngms.cis.cisworkflow.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author : prateekgoel
 * @since : 20/05/21, Thu
 **/
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Outlink {

    @JsonProperty("Action")
    private String action;

    private String actionCaption;

    private String nextNodeRoleId;

    @JsonProperty("NextNodeID")
    private Long nextNodeID;

    @JsonProperty("Xref")
    private String xref;

    @JsonProperty("StatusInternal")
    private String statusInternal;

    @JsonProperty("StatusExternal")
    private String statusExternal;

    @JsonProperty("NoticationTem")
    private Long notificationTemplateId;

}
