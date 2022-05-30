package com.dw.ngms.cis.persistence.domains.user;

import com.dw.ngms.cis.enums.UserLeaveCalendarStatus;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.Date;

/**
 * @author : prateekgoel
 * @since : 04/12/20, Fri
 **/
@Data
@Entity
@Table(name = "USER_LEAVECALENDAR")
public class UserLeaveCalendar {

    @Id
    @Column(name = "LEAVEID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_leavecalendar_seq")
    @SequenceGenerator(name = "user_leavecalendar_seq", sequenceName = "USER_LEAVECALENDAR_SEQ", allocationSize = 1)
    private Long leaveId;

    @Column(name = "USERID")
    private Long userId;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "STATUS")
    private UserLeaveCalendarStatus status;

    @Temporal(TemporalType.DATE)
    @Column(name = "STARTDATE")
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(name = "ENDDATE")
    private Date endDate;

    @Column(name = "MANAGER_COMMENT")
    private String managerComment;

    @Column(name = "FROM_TIME")
    @DateTimeFormat(pattern = "hh:mm a")
    private LocalTime fromTime;

    @Column(name = "TO_TIME")
    @DateTimeFormat(pattern = "hh:mm a")
    private LocalTime toTime;


    @Column(name = "TIME_SELECTED")
    private Long timeSelected;

    @Column(name = "LEAVE_TYPE_ID")
    private Long leaveTypeId;

}
