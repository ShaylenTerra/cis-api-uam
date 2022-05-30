package com.dw.ngms.cis.enums.converter;

import com.dw.ngms.cis.enums.UserLeaveCalendarStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

/**
 * @author : prateekgoel
 * @since : 04/12/20, Fri
 **/
@Converter(autoApply = true)
public class UserLeaveCalenderStatusConvertor implements AttributeConverter<UserLeaveCalendarStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(UserLeaveCalendarStatus userLeaveCalendarStatus) {
        if(userLeaveCalendarStatus == null)
            return null;
        return userLeaveCalendarStatus.getUserLeaveCalendarStatus();
    }

    @Override
    public UserLeaveCalendarStatus convertToEntityAttribute(Integer dbData) {
        return Stream.of(UserLeaveCalendarStatus.values())
                .filter(u -> u.getUserLeaveCalendarStatus().equals(dbData))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);

    }
}
