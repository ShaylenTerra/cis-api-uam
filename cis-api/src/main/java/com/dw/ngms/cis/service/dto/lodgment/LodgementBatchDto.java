package com.dw.ngms.cis.service.dto.lodgment;

import com.dw.ngms.cis.persistence.domains.lodgement.LodgementBatchSgDocument;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author prateek on 21-04-2022
 */
@Data
public class LodgementBatchDto {

    private Long batchId;

    private Long batchNumber;

    private LocalDateTime createdOn;

    private Long userId;

    private Long provinceId;

    private Long draftId;

    private String batchNumberText;

    @JsonProperty("lodgementBatchSgDocuments")
    private Set<LodgementBatchSgDocumentDto> lodgementBatchSgDocumentDtos;
}
