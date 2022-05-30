package com.dw.ngms.cis.cisworkflow.persistence.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author : prateekgoel
 * @since : 20/05/21, Thu
 **/
@Data
@Entity
@Table(name = "WORKFLOWPROCESS")
public class WorkflowProcess {

    @Id
    @Column(name = "PROCESSID")
    private Long processId;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ITEMID_MODULE")
    private Integer itemIdModule;

    @Column(name = "PROVINCEID")
    private Long provinceId;

    @Column(name = "ISACTIVE")
    private Integer isActive;

    @Lob
    @Column(name = "CONFIGURATION")
    private String configuration;

    @Lob
    @Column(name = "DESIGNDATA")
    private String designData;

    @Column(name = "DRAFT_VERSION")
    private BigDecimal draftVersion;

    @Column(name = "PUBLISHED_VERSION")
    private BigDecimal publishedVersion;

    @Column(name = "PARENT_PROCESSID")
    private Long parentProcessId;

    @Column(name = "DATED")
    private LocalDateTime dated;

    @Column(name = "USERID")
    private Long userId;

}
