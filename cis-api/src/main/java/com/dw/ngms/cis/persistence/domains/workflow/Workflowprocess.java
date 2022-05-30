package com.dw.ngms.cis.persistence.domains.workflow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "WORKFLOWPROCESS")
public class Workflowprocess {

    @Id
    @GeneratedValue(generator = "workflowprocess_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "workflowprocess_seq", sequenceName = "WORKFLOWPROCESS_SEQ", allocationSize = 1)
    @Column(name = "PROCESSID")
    private Long processid;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ITEMID_MODULE")
    private Long itemidModule;

    @Column(name = "PROVINCEID")
    private Long provinceid;

    @Column(name = "ISACTIVE")
    private Long isactive;

    @Column(name = "DRAFT_VERSION")
    private Long draftVersion;

    @Column(name = "PUBLISHED_VERSION")
    private Long publishedVersion;

    @Column(name = "PARENT_PROCESSID")
    private Long parentProcessid;

    @Column(name = "DATED")
    private java.sql.Timestamp dated;

    @Column(name = "USERID")
    private Long userid;

    @Column(name = "CONFIGURATION")
    @Lob
    private String configuration;

    @Column(name = "DESIGNDATA")
    @Lob
    private String designdata;


}
