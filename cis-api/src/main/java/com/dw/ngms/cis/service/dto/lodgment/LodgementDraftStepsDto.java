package com.dw.ngms.cis.service.dto.lodgment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Data
public class LodgementDraftStepsDto {

    @NotNull
    private Long draftId;

    private Long stepId;

    @NotNull
    private Long stepNo;

    @NotNull
    private Long reasonItemId;

    private String requestReason;

    private String documentType;

    private Long documentItemId;

    @JsonProperty("lodgementDraftRequests")
    private Collection<LodgementDraftRequestDto> lodgementDraftRequestDtos;
}
