package com.dw.ngms.cis.service.dto.lodgment;

import com.dw.ngms.cis.persistence.domains.lodgement.LodgementDraftDocument;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import lombok.Data;

import java.util.Collection;

/**
 * @author prateek on 03-01-2022
 */
@Data
public class LodgementDraftRequestDto {

    private Long requestId;

    private Long stepId;

    private Long locationId;

    private String lpi;

    private String designation;

    @JsonRawValue
    private String parentParcels;

    private Long outcomeIdReservation;

    @JsonProperty("lodgementDraftDocuments")
    private Collection<LodgementDraftDocumentDto> lodgementDraftDocumentDtos;

    private Long reservationWorkflowId;

    private String status;

    private String reservationReferenceNumber;

}
