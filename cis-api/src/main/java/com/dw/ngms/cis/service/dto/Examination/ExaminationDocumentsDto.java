package com.dw.ngms.cis.service.dto.Examination;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class ExaminationDocumentsDto {


    private long DocumentID;

    private String Name;

    private String Extension;

    private LocalDateTime UploadDate;

    private char STATUS;

    private int docTypeId;

    private int ExamId;


}
