package com.dw.ngms.cis.persistence.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "SEARCH_TEMPLATE_AUDIT")
public class SearchTemplateAudit {

    @Id
    @GeneratedValue(generator = "search_template_audit_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "search_template_audit_seq",sequenceName = "SEARCH_TEMPLATE_AUDIT_SEQ", allocationSize = 1)
    @Column(name = "SEARCH_TEMPLATE_AUDITID")
    private Long searchTemplateAuditId;

    @Column(name = "USERID")
    private Long userId;

    @Column(name = "CREATEDDATE")
    private LocalDateTime createdDate;

    @Column(name = "TOTALRECORDS")
    private Integer totalRecords;

    @Column(name = "FOUNDRECORDS")
    private Integer foundRecords;

    @Column(name = "TEMPLATEPATH")
    private String templatePath;

    @Column(name = "TEMPLATETYPE")
    private String templateType;


}
