package com.dw.ngms.cis.service.mapper.Examination;

import com.dw.ngms.cis.persistence.domains.examination.Examination;
import com.dw.ngms.cis.persistence.domains.examination.Examination_Documents;
import com.dw.ngms.cis.service.dto.Examination.ExaminationDocumentsDto;
import com.dw.ngms.cis.service.dto.Examination.ExaminationDto;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

public interface Examination_DocumentsMapper {
    @Mappings({


            @Mapping(target = "name", source = "name"),
            @Mapping(target = "Extension", source = "Extension"),
            @Mapping(target = "UploadDate", source = "UploadDate"),
            @Mapping(target = "ExamId", source = "ExamId"),
            @Mapping(target = "STATUS", source = "STATUS"),
    })
    Examination_Documents examinationDocumentsDtoToExaminationDocuments (ExaminationDocumentsDto examinationDocumentsDto);

    ExaminationDocumentsDto examinaionDocumentsToExaminationDocumentsDto (Examination_Documents examination_documents);
}
