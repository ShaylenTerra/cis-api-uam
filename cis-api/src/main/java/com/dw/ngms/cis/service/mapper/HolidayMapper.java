package com.dw.ngms.cis.service.mapper;

import com.dw.ngms.cis.persistence.domains.HolidayCalendar;
import com.dw.ngms.cis.service.dto.HolidayCalendarDto;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

/**
 * @author : prateekgoel
 * @since : 27/01/21, Wed
 **/
@Mapper(componentModel = "spring")
public interface HolidayMapper {


    HolidayCalendar holidayDtoToHoliday(HolidayCalendarDto holidayCalendarDto);

    @InheritInverseConfiguration
    HolidayCalendarDto holidayCalendarToHolidayCalendarDto(HolidayCalendar holidayCalendar);

}
