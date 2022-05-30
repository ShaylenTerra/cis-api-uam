package com.dw.ngms.cis.service.dto.lodgment;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author prateek on 12-04-2022
 */
@Data
public class LodgementDraftDocumentDto {

    private Long documentId;

    private Long stepId;

    private Long requestId;

    private Long draftId;

    private String documentType;

    private Long documentItemId;

    private String purposeType;

    private Long purposeItemId;

    private Long userId;

    private String notes;

    @NotNull
    private MultipartFile document;

    private String documentName;

    private LocalDateTime dated;

}
