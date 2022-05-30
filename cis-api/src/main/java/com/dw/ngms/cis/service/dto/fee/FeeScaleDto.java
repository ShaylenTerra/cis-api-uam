package com.dw.ngms.cis.service.dto.fee;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author : prateekgoel
 * @since : 27/11/20, Fri
 **/
@Data
public class FeeScaleDto {

    private Long feeScaleId;

    @NotNull
    private String feeScaleName;

    @NotNull
    private Long userId;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime effectiveDate;

    @NotNull
    private String description;

    @NotNull
    private MultipartFile multipartFile;

    private String fileName;

}
