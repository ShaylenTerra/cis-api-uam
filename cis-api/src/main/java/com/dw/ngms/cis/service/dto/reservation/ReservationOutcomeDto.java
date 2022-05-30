package com.dw.ngms.cis.service.dto.reservation;

import com.dw.ngms.cis.persistence.domains.reservation.ReservationDraftSteps;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

/**
 * @author prateek on 02-02-2022
 */
@Data
public class ReservationOutcomeDto {

    private Long outcomeId;

    private Long workflowId;

    private Long draftId;

    private Long stepId;

    private Long requestId;

    private Long locationId;

    private String locationName;

    private Long reasonItemId;

    private Long parcel;

    private Long portion;

    private String lpi;

    private String designation;

    private Long statusItemId;

    private Long ownerUserId;

    private LocalDateTime issueDate;

    private LocalDateTime expiryDate;

    private String algorithm;
}
