package com.dw.ngms.cis.cisworkflow.persistence.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author : prateekgoel
 * @since : 20/05/21, Thu
 **/
@Data
@Entity
@Table(name = "WORKFLOWPROCESS_AUDIT")
public class WorkflowprocessAudit {

    @Id
    @Column(name = "ID")
    private Integer ID;

    @Column(name = "PROCESSID")
    private Integer processid;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ITEMID_MODULE")
    private String itemidModule;

    @Column(name = "PROVINCEID")
    private String provinceid;

    @Column(name = "ISACTIVE")
    private Integer isactive;

    @Lob
    @Column(name = "CONFIGURATION")
    private String configuration;

    @Lob
    @Column(name = "DESIGNDATA")
    private String designdata;

    @Column(name = "DRAFT_VERSION")
    private String draftVersion;

    @Column(name = "PUBLISHED_VERSION")
    private String publishedVersion;

    @Column(name = "PARENT_PROCESSID")
    private Integer parentProcessid;

    @Column(name = "DATED")
    private Date dated;

    @Column(name = "USERID")
    private String userid;
}
