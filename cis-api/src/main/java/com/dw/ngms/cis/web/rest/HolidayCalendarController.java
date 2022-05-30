package com.dw.ngms.cis.web.rest;

import com.dw.ngms.cis.configuration.AppConstants;
import com.dw.ngms.cis.service.HolidayCalendarService;
import com.dw.ngms.cis.service.dto.HolidayCalendarDto;
import com.dw.ngms.cis.web.annotation.BaseResponse;
import com.dw.ngms.cis.web.request.NewHolidayRequest;
import com.dw.ngms.cis.web.response.CreateResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * Created by nirmal on 2020/11/18.
 */
@BaseResponse
@RestController
@AllArgsConstructor
@RequestMapping(AppConstants.API_BASE_MAPPING + "/holiday")
public class HolidayCalendarController {


    private final HolidayCalendarService holidayCalendarService;

    /**
     * @param year holiday year
     * @return ResponseEntity<List < HolidayCalendar>>
     */
    @GetMapping("/list")
    public ResponseEntity<Collection<HolidayCalendarDto>>
    listHolidaysByYear(@RequestParam @NotNull final Long year) {
        return ResponseEntity.ok()
                .body(holidayCalendarService.listHolidaysByYear(year));
    }

    /**
     * @param newHolidayRequestBody {@link NewHolidayRequest}
     * @return ResponseEntity<CreateResponse>
     */
    @PostMapping("/add")
    public ResponseEntity<CreateResponse> listIssueLog(
            @RequestBody @NotNull final NewHolidayRequest newHolidayRequestBody) {
        return ResponseEntity.ok()
                .body(holidayCalendarService.createHoliday(newHolidayRequestBody));
    }


    /**
     * @param holidayCalendarDto {@link HolidayCalendarDto}
     */
    @PostMapping("/deleteHoliday")
    public void deleteHoliday(@RequestBody @Valid final HolidayCalendarDto holidayCalendarDto) {
        holidayCalendarService.deleteHoliday(holidayCalendarDto);
    }
}
