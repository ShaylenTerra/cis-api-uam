package com.dw.ngms.cis.enums;

import java.util.stream.Stream;

/**
 * @author : prateekgoel
 * @since : 04/12/20, Fri
 **/
public enum UserLeaveCalendarStatus {
    PENDING(1),REJECTED(0),APPROVED(2);

    private Integer status;

    UserLeaveCalendarStatus(Integer status) {
        this.status = status;
    }

    public static UserLeaveCalendarStatus of(Integer userLeaveCalendarStatus) {
        return Stream.of(UserLeaveCalendarStatus.values())
                .filter(userLeaveCalendarStatus1   -> userLeaveCalendarStatus1
                        .getUserLeaveCalendarStatus().equals(userLeaveCalendarStatus))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public Integer getUserLeaveCalendarStatus() {
        return status;
    }
}
