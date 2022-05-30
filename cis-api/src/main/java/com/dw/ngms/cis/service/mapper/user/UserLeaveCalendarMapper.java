package com.dw.ngms.cis.service.mapper.user;

import com.dw.ngms.cis.enums.UserLeaveCalendarStatus;
import com.dw.ngms.cis.persistence.domains.user.UserLeaveCalendar;
import com.dw.ngms.cis.service.dto.user.UserLeaveCalendarDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

/**
 * @author : prateekgoel
 * @since : 04/12/20, Fri
 **/
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserLeaveCalendarMapper {

    UserLeaveCalendar userLeaveCalendarDtoToUserLeaveCalendar(UserLeaveCalendarDto userLeaveCalendarDto);

    UserLeaveCalendarDto userLeaveCalendarToUserLeaveCalendarDto(UserLeaveCalendar userLeaveCalendar);

    default String toString(UserLeaveCalendarStatus userLeaveCalendarStatus) {
        return userLeaveCalendarStatus.name();
    }

    default UserLeaveCalendarStatus toEnum(Integer code) {
        return UserLeaveCalendarStatus.of(code);
    }

}
