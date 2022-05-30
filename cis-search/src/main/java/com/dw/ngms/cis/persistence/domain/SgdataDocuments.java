package com.dw.ngms.cis.persistence.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "SGDATA_DOCUMENTS")
public class SgdataDocuments {

    @Id
    @GeneratedValue(generator = "sgdata_documents_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "sgdata_documents_seq", sequenceName = "SGDATA_DOCUMENTS_SEQ", allocationSize = 1)
    @Column(name = "RECORD_ID")
    private Long recordId;


    @Column(name = "DOCUMENTNO")
    private String documentno;

    @Column(name = "SGNO")
    private String sgno;


    @Column(name = "TITLE")
    private String title;

    @Column(name = "SCANDATE")
    private LocalDateTime scandate;

    @Column(name = "PAGENO")
    private Long pageno;

    @Column(name = "FILE_SIZE")
    private Long fileSize;

    @Column(name = "URL")
    private String url;

    @Column(name = "PREVIEW")
    private String preview;

    @Column(name = "THUMBNAIL")
    private String thumbnail;

    @Column(name = "DOCUMENT_ID")
    private Long documentId;


}
