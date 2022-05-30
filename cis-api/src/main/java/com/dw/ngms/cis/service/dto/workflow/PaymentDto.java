package com.dw.ngms.cis.service.dto.workflow;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author : prateekgoel
 * @since : 06/01/21, Wed
 **/
@Data
public class PaymentDto {

    private Long paymentId;

    private Long documentTypeId;

    private MultipartFile file;

    private Date paymentDate;

    private String paymentReferenceNo;

    private Double paidAmount;

    private Double invoiceAmount;

    private Long workflowId;

    private String comments;

    private Long userId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime invoiceDueDate;

    private String invoiceComment;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime invoiceGenerationDate;
}
