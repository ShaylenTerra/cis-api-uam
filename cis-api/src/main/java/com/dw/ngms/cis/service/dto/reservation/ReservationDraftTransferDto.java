package com.dw.ngms.cis.service.dto.reservation;

import lombok.Data;

/**
 * @author prateek on 20-03-2022
 */
@Data
public class ReservationDraftTransferDto {

    private Long transferId;

    private Long draftId;

    private Long outcomeId;
}
