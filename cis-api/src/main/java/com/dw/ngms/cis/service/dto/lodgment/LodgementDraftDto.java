package com.dw.ngms.cis.service.dto.lodgment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
public class LodgementDraftDto {

    @JsonView(LodgementDraftDtoView.Public.class)
    private Long draftId;

    @JsonView(LodgementDraftDtoView.Public.class)
    private Long userId;

    @NotNull
    @JsonView(LodgementDraftDtoView.Public.class)
    private String name;

    @JsonView(LodgementDraftDtoView.Public.class)
    private Long provinceId;

    @JsonView(LodgementDraftDtoView.Public.class)
    private String provinceName;

    @JsonView(LodgementDraftDtoView.Public.class)
    private LocalDateTime dated;

    @NotNull
    @JsonView(LodgementDraftDtoView.Public.class)
    private String description;

    @JsonView(LodgementDraftDtoView.Public.class)
    private LocalDateTime updatedOn;

    @JsonView(LodgementDraftDtoView.Internal.class)
    private Long applicant;

    @JsonView(LodgementDraftDtoView.Internal.class)
    private Long applicantUserId;

    @JsonView(LodgementDraftDtoView.Internal.class)
    private String deliveryMethod;

    @JsonView(LodgementDraftDtoView.Internal.class)
    private String deliveryMethodItemId;

    @JsonView(LodgementDraftDtoView.Internal.class)
    private Long workflowId;

    @JsonView(LodgementDraftDtoView.Public.class)
    private String userName;

    @JsonView(LodgementDraftDtoView.Internal.class)
    @JsonProperty("lodgementDraftAnnexures")
    private Collection<LodgementDraftAnnexureDto> lodgementDraftAnnexureDto;

    @JsonView({LodgementDraftDtoView.Internal.class})
    @JsonProperty("lodgementDraftSteps")
    private Collection<LodgementDraftStepsDto> lodgementDraftStepsDto;


    @JsonView({LodgementDraftDtoView.Internal.class})
    @JsonProperty("lodgementBatch")
    private LodgementBatchDto lodgementBatchDto;


    @JsonView({LodgementDraftDtoView.Internal.class})
    @JsonProperty("lodgementDraftPayments")
    private Collection<LodgementDraftPaymentDto> lodgementDraftPaymentsDto;

    @JsonView(LodgementDraftDtoView.Internal.class)
    private String email;

    @JsonView(LodgementDraftDtoView.Internal.class)
    private Long isPrimaryEmail;

    @NotNull
    @JsonView({LodgementDraftDtoView.Public.class})
    private Long processId;

    @JsonView({LodgementDraftDtoView.Public.class})
    private String fileRef;

    @JsonView({LodgementDraftDtoView.Public.class})
    private Long isActive;
}
