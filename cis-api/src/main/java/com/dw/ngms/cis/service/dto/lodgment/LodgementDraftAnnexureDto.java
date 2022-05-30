package com.dw.ngms.cis.service.dto.lodgment;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author prateek on 31-12-2021
 */
@Data
public class LodgementDraftAnnexureDto {

    private Long annexureId;

    @NotNull
    private Long draftId;

    private String type;

    @NotNull
    private Long typeItemId;

    @NotNull
    private MultipartFile document;

    private String name;

    private String notes;

    private Long userId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dated;

}
