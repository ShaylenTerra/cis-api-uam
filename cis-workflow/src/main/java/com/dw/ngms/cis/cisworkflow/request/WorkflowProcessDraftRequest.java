package com.dw.ngms.cis.cisworkflow.request;

import lombok.Data;

import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;

/**
 * @author : prateekgoel
 * @since : 20/05/21, Thu
 **/
@Data
public class WorkflowProcessDraftRequest {

    private Long processId;

    private String name;

    private String description;

    private Integer itemIdModule;

    private Long provinceId;

    private String configuration;

    private BigDecimal draftVersion;

    private Long userId;

    private String context;

    @XmlElement
    private String designData;
}
