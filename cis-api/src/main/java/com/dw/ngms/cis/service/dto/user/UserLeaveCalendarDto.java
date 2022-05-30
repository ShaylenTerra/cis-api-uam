package com.dw.ngms.cis.service.dto.user;

import com.dw.ngms.cis.enums.UserLeaveCalendarStatus;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.time.LocalTime;

/**
 * @author : prateekgoel
 * @since : 04/12/20, Fri
 **/
@Data
public class UserLeaveCalendarDto {

    private Long leaveId;

    private Long userId;

    private String description;

    private UserLeaveCalendarStatus status;

    private Date startDate;

    private Date endDate;

    private MultipartFile multipartFile;

    private String userName;

    private String managerComment;

    private Long documentCount;


    @DateTimeFormat(pattern = "hh:mm a")
    private LocalTime fromTime;


    @DateTimeFormat(pattern = "hh:mm a")
    private LocalTime toTime;

    private Long timeSelected;

    private Long leaveTypeId;

    private String leaveTypeCaption;

}
