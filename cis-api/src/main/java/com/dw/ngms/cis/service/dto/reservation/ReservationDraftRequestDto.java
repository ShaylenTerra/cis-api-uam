package com.dw.ngms.cis.service.dto.reservation;

import com.dw.ngms.cis.persistence.domains.reservation.ReservationDraftSteps;
import lombok.Data;

import javax.persistence.*;

/**
 * @author prateek on 03-01-2022
 */
@Data
public class ReservationDraftRequestDto {

    private Long draftRequestId;

    private Long stepId;

    private Long recordId;

    private Long locationId;

    private String lpi;

    private String designation;

    private String parcel;

    private String portion;

    private Long draftOutcomeId;

    private Long recordTypeId;

    private String location;
}
