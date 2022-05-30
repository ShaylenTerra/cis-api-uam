package com.dw.ngms.cis.persistence.domains;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "M_TEMPLATES_AUDIT")
public class TemplatesAudit implements Serializable {

    @Id
    @Column(name = "TEMPLTATESAUDITID")
    private Long templatesAuditId;

    @Column(name = "TEMPLATEID")
    private Long templateId;

    @Column(name = "TEMPLATENAME")
    private String templateName;

    @Column(name = "ITEMID_MODULE")
    private Long itemIdModule;

    @Column(name = "ISACTIVE")
    private Integer isActive;

    @Column(name = "EMAIL_DETAILS")
    private String emailDetails;

    @Column(name = "SMS_DETAILS")
    private String smsDetails;

    @Column(name = "PDF_DETAILS")
    private String pdfDetails;

    @Column(name = "PDF")
    private Long pdf;

    @Column(name = "SMS")
    private Long sms;

    @Column(name = "EMAIL")
    private Long email;

    @Column(name = "USERID")
    private Long userId;

    @Column(name = "DATED")
    private LocalDateTime dated;

}
