package com.dw.ngms.cis.service.dto.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;

@Data
public class ReservationDraftDto {

    @JsonView(ReservationDraftDtoView.Public.class)
    private Long draftId;

    @JsonView(ReservationDraftDtoView.Public.class)
    private Long userId;

    @NotNull
    @JsonView(ReservationDraftDtoView.Public.class)
    private String name;

    @JsonView(ReservationDraftDtoView.Public.class)
    private Long provinceId;

    @JsonView(ReservationDraftDtoView.Public.class)
    private String provinceName;

    @JsonView(ReservationDraftDtoView.Public.class)
    private LocalDateTime dated;

    @NotNull
    @JsonView(ReservationDraftDtoView.Public.class)
    private String purpose;

    @JsonView(ReservationDraftDtoView.Public.class)
    private LocalDateTime updatedOn;

    @JsonView(ReservationDraftDtoView.Internal.class)
    private Long applicant;

    @JsonView(ReservationDraftDtoView.Internal.class)
    private Long applicantUserId;

    @JsonView(ReservationDraftDtoView.Internal.class)
    private LocalDateTime surveyDate;

    @JsonView(ReservationDraftDtoView.Internal.class)
    private String deliveryMethod;

    @JsonView(ReservationDraftDtoView.Internal.class)
    private String deliveryMethodItemId;

    @JsonView(ReservationDraftDtoView.Internal.class)
    private Long workflowId;

    @JsonView(ReservationDraftDtoView.Public.class)
    private String userName;

    @JsonView(ReservationDraftDtoView.Internal.class)
    @JsonProperty("reservationDraftDocuments")
    private Collection<ReservationDraftDocumentsDto> reservationDraftDocumentsDto;

    @JsonView({ReservationDraftDtoView.Internal.class})
    @JsonProperty("reservationDraftSteps")
    private Collection<ReservationDraftStepsDto> reservationDraftStepsDto;

    @JsonView(ReservationDraftDtoView.Internal.class)
    private String email;

    @JsonView(ReservationDraftDtoView.Internal.class)
    private Long isPrimaryEmail;

    @NotNull
    @JsonView({ReservationDraftDtoView.Public.class})
    private Long processId;

    @JsonView({ReservationDraftDtoView.Public.class})
    private Long toUserId;

    @JsonView({ReservationDraftDtoView.Internal.class})
    private String fileRef;
}
