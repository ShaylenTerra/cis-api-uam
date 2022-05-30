package com.dw.ngms.cis.service.dto.reservation;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author prateek on 31-12-2021
 */
@Data
public class ReservationDraftDocumentsDto {

    private Long documentId;

    @NotNull
    private Long draftId;

    @NotNull
    private Long typeId;

    @NotNull
    private MultipartFile document;

    private String documentName;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dated;

}
