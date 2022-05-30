package com.dw.ngms.cis.service.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * @author : prateekgoel
 * @since : 05/12/20, Sat
 **/
@Data
public class OfficeTimeDto {

    private Long officeTimeId;

    private Long userId;

    private Long provinceId;

    private String Description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    private LocalDate fromDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MMM/yyyy")
    private LocalDate toDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm a")
    private LocalTime fromTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm a")
    private LocalTime toTime;

    private Long isActive;

    private String officeTimingType;
}
