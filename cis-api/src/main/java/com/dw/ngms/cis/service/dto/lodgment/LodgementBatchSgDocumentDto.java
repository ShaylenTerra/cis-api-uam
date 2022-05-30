package com.dw.ngms.cis.service.dto.lodgment;

import com.dw.ngms.cis.persistence.domains.lodgement.LodgementBatch;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author prateek on 21-04-2022
 */
@Data
public class LodgementBatchSgDocumentDto {

    private Long sgDocumentId;

    private Long docNumber;

    private Long batchId;

    private String documentType;

    private Long docTypeItemId;

    private Long provinceId;

    private String docNumberText;

    private Long resOutcomeId;

    private LocalDateTime createdOn;
}
