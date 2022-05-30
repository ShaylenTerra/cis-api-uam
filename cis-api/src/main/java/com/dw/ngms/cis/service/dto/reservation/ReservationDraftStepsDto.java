package com.dw.ngms.cis.service.dto.reservation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Collection;

@Data
public class ReservationDraftStepsDto {

    @NotNull
    @JsonView({ReservationDraftDtoView.Internal.class, ReservationDraftStepsDtoViews.Public.class})
    private Long draftId;

    @JsonView({ReservationDraftDtoView.Internal.class,ReservationDraftStepsDtoViews.Internal.class})
    private Long stepId;

    @NotNull
    @JsonView({ReservationDraftDtoView.Internal.class,ReservationDraftStepsDtoViews.Public.class})
    private Long stepNo;

    @NotNull
    @JsonView({ReservationDraftDtoView.Internal.class,ReservationDraftStepsDtoViews.Public.class})
    private Long reasonItemId;

    @JsonView({ReservationDraftDtoView.Internal.class,ReservationDraftStepsDtoViews.Internal.class})
    private String requestReason;

    @JsonView({ReservationDraftDtoView.Internal.class,ReservationDraftStepsDtoViews.Public.class})
    private Long parcelRequested;

    @JsonView({ReservationDraftDtoView.Internal.class,ReservationDraftStepsDtoViews.Public.class})
    @JsonRawValue
    private String otherData;

    @JsonView({ReservationDraftDtoView.Internal.class,ReservationDraftStepsDtoViews.Internal.class})
    @JsonProperty("reservationDraftRequests")
    private Collection<ReservationDraftRequestDto> reservationDraftRequestDto;

    @JsonView({ReservationDraftDtoView.Internal.class,ReservationDraftStepsDtoViews.Internal.class})
    @JsonProperty("reservationDraftRequestOutcome")
    private Collection<ReservationDraftRequestOutcomeDto> reservationDraftRequestOutcomeDto;

    @JsonView({ReservationDraftDtoView.Internal.class,ReservationDraftStepsDtoViews.Internal.class})
    @JsonProperty("reservationOutcome")
    private Collection<ReservationOutcomeDto> reservationOutcomeDto;
}
