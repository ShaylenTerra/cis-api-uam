package com.dw.ngms.cis.service.dto;

import lombok.Data;

import java.util.Date;

/**
 * @author : prateekgoel
 * @since : 27/01/21, Wed
 **/
@Data
public class HolidayCalendarDto {

    private Long holidayId;

    private Date holidayDate;

    private String description;

    private Integer status;

}
