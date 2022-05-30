package com.dw.ngms.cis.cisworkflow.persistence.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "M_TEMPLATES")
public class MTemplates {

    @Id
    @Column(name = "TEMPLATEID")
    private Long templateId;

    @Column(name = "TEMPLATENAME")
    private String templateName;

    @Column(name = "ITEMID_MODULE")
    private Long itemIdModule;

    @Column(name = "ISACTIVE")
    private Long isActive;

    @Column(name = "EMAIL_DETAILS")
    private String emailDetails;

    @Column(name = "SMS_DETAILS")
    private String smsDetails;

    @Lob
    @Column(name = "PDF_DETAILS")
    private String pdfDetails;

    @Column(name = "PDF")
    private Long pdf;

    @Column(name = "SMS")
    private Long sms;

    @Column(name = "EMAIL")
    private Long email;
}
