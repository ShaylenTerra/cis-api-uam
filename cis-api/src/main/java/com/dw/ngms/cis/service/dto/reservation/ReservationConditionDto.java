package com.dw.ngms.cis.service.dto.reservation;

import lombok.Data;

import javax.persistence.Column;

/**
 * @author prateek on 03-02-2022
 */
@Data
public class ReservationConditionDto {

    private Long conditionId;

    private Long draftId;

    private Long stepId;

    private String condition;

    private String conditionAlphabet;

    private String reason;

    private Long stepNo;
}
