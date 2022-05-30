package com.dw.ngms.cis.service.dto.reservation;

import com.dw.ngms.cis.persistence.domains.reservation.ReservationDraftSteps;
import lombok.Data;

import javax.persistence.*;

/**
 * @author prateek on 03-01-2022
 */
@Data
public class ReservationDraftRequestOutcomeDto {


    private Long draftOutcomeId;

    private Long stepId;

    private String designation;

    private String parcel;

    private String portion;

    private Long locationId;

    private String location;

    private Long recordTypeId;

    private String lpi;


}
