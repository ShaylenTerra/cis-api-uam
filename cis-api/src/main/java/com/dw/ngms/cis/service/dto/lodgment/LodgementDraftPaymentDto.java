package com.dw.ngms.cis.service.dto.lodgment;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author prateek on 06-04-2022
 */
@Data
public class LodgementDraftPaymentDto {

    private Long payId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime payDate;

    private String amount;

    private String refNumber;

    private String notes;

    private String paymentMethod;

    private Long payMethodItemId;

    private Long userId;

    private String status;

    private Long statusItemId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dated;

    private String receiptNo;

    private Long draftId;

    private MultipartFile document;

    private String docName;
}
