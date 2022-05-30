package com.dw.ngms.cis.service;

import com.dw.ngms.cis.persistence.domains.HolidayCalendar;
import com.dw.ngms.cis.persistence.repository.HolidayCalendarRepository;
import com.dw.ngms.cis.service.dto.HolidayCalendarDto;
import com.dw.ngms.cis.service.mapper.HolidayMapper;
import com.dw.ngms.cis.web.request.NewHolidayRequest;
import com.dw.ngms.cis.web.response.CreateResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Created by nirmal on 2020/11/18.
 */
@Service
@AllArgsConstructor
@Slf4j
public class HolidayCalendarService {

    private final HolidayMapper holidayMapper;

    private final HolidayCalendarRepository holidayCalendarRepository;

    public Collection<HolidayCalendarDto> listHolidaysByYear(Long holidayYear) {
        return holidayCalendarRepository.findByHolidayYear(holidayYear)
                .stream()
                .map(holidayMapper::holidayCalendarToHolidayCalendarDto)
                .collect(Collectors.toList());
    }

    public CreateResponse createHoliday(NewHolidayRequest newHolidayRequestBody) {
        HolidayCalendar holidayObj = new HolidayCalendar();
        try {
            holidayObj.setHolidayDate(new SimpleDateFormat("dd/MM/yyyy").parse(newHolidayRequestBody.getHolidayDate()));
            holidayObj.setDescription(newHolidayRequestBody.getDescription());
            holidayObj.setStatus(1);
            this.holidayCalendarRepository.save(holidayObj);
            return CreateResponse.builder().created(true).build();
        } catch (Exception e) {
            log.error(e.getMessage());
            return CreateResponse.builder().created(false).build();
        }
    }

    public void deleteHoliday(HolidayCalendarDto holidayCalendarDto) {
        HolidayCalendar holidayCalendar = holidayMapper
                .holidayDtoToHoliday(holidayCalendarDto);
        holidayCalendarRepository.deleteById(holidayCalendar.getHolidayId());
    }

    public Boolean checkIfHolidayForDate(final LocalDate date) {
        //LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        HolidayCalendar byHolidayDateAndStatus = holidayCalendarRepository
                .findByHolidayDateAndStatus(date, 1);

        return null != byHolidayDateAndStatus;

    }
}
