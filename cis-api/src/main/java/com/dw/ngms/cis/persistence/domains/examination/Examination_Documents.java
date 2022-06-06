package com.dw.ngms.cis.persistence.domains.examination;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class Examination_Documents {


    // @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Id
    @GeneratedValue(generator = "ISEQ$$_102858", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "ISEQ$$_102858",sequenceName = "ISEQ$$_102858", allocationSize = 1)
    @Column(name = "DOC_ID",insertable= false)
    private long DocumentID;

    @NotNull(message = "Document name is required.")
    @Column(name="DOC_NAME")
    private String Name;

    @Column(name="EXTENSION")
    private String Extension;

    @Column(name="UPLOADDATE")
    private LocalDateTime UploadDate;

    @Column(name="STATUS")
    private char STATUS;

    @Column(name="DOC_TYPE_ITEMID")
    private int docTypeId;

    @Column(name="EXAM_ID")
    private int ExamId;



}
