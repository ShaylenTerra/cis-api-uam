package com.dw.ngms.cis.web.rest;

import com.dw.ngms.cis.configuration.AppConstants;
import com.dw.ngms.cis.enums.OfficeTimingType;
import com.dw.ngms.cis.service.OfficeTimingsService;
import com.dw.ngms.cis.service.dto.OfficeTimeDto;
import com.dw.ngms.cis.service.mapper.OfficeTimeMapper;
import com.dw.ngms.cis.web.annotation.ApiPageable;
import com.dw.ngms.cis.web.annotation.BaseResponse;
import com.dw.ngms.cis.web.response.UpdateResponse;
import com.dw.ngms.cis.web.util.PaginationUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Collection;

/**
 * Created by nirmal on 2020/11/18.
 */
@BaseResponse
@RestController
@AllArgsConstructor
@RequestMapping(AppConstants.API_BASE_MAPPING + "/office-timings")
public class OfficeTimingResource {


    private final OfficeTimingsService officeTimingsService;
    private final OfficeTimeMapper officeTimeMapper;

    /**
     * @param provinceId provinceId
     * @param pageable   {@link Pageable}
     * @return collection of officeTimeDto
     */
    @GetMapping(value = "/list")
    @ApiPageable
    public ResponseEntity<Collection<OfficeTimeDto>> listOfficeTimings(@RequestParam("provinceId") @NotNull final Long provinceId,
                                                                       @RequestParam final OfficeTimingType officeTimingType,
                                                                       @ApiIgnore final Pageable pageable) {
        Page<OfficeTimeDto> officeTimeDtos = officeTimingsService.listOfficeTimings(provinceId, officeTimingType, pageable);
        HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(officeTimeDtos);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(officeTimeDtos.getContent());

    }

    /**
     * @param userId userId
     * @return Collection<OfficeTimeDto>
     */
    @GetMapping(value = "/listByUserId")
    public ResponseEntity<Collection<OfficeTimeDto>> getOfficeTimingForUserId(@RequestParam final Long userId) {
        return ResponseEntity.ok()
                .body(officeTimingsService.getOfficeHolidayByUserId(userId));
    }

    /**
     * @param officeTimeDto officeTimeDto
     * @return OfficeTimeDto
     */
    @PostMapping("/add")
    public ResponseEntity<OfficeTimeDto> addOfficeTiming(@RequestBody @NotNull final OfficeTimeDto officeTimeDto) {
        return ResponseEntity.ok().body(officeTimingsService.addOfficeTime(officeTimeDto));
    }

    /**
     * @param status       1/0
     * @param officeTimeId office time id
     * @return UpdateResponse
     */
    @GetMapping("/update")
    public ResponseEntity<UpdateResponse> updateOfficeTimeStatus(@RequestParam @NotNull final Long status,
                                                                 @RequestParam @NotNull final Long officeTimeId) {
        return ResponseEntity.ok()
                .body(UpdateResponse.builder()
                        .update(officeTimingsService.updateOfficeTimeStatus(status, officeTimeId))
                        .build()
                );
    }

    /**
     * @param provinceId provinceId
     * @param fromDate   fromDate
     * @return {@link OfficeTimeDto}
     */
    @GetMapping(value = "/getOfficeTimingByProvinceAndDate")
    public ResponseEntity<OfficeTimeDto> getOfficeTimingByProvinceAndDate(
            @RequestParam final Long provinceId,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE, pattern = "dd/MMM/yyyy") final LocalDate fromDate) {

        return ResponseEntity.ok()
                .body(officeTimingsService
                        .getOfficeTimingForFromDateAndProvinceId(provinceId, fromDate));
    }
}
