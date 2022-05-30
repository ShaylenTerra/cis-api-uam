package com.dw.ngms.cis.cisworkflow.service;

import com.dw.ngms.cis.cisworkflow.persistence.domain.MOfficeTimings;
import com.dw.ngms.cis.cisworkflow.persistence.repository.OfficeTimingRepository;
import com.dw.ngms.cis.cisworkflow.utility.WorkflowUtility;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * @author : prateekgoel
 * @since : 20/05/21, Thu
 **/
@Service
@AllArgsConstructor
public class OfficeTimingService {


    private final OfficeTimingRepository officeTimingRepository;

    public ResponseEntity<?> addOfficeTiming(Map<String, String> officeTimingRequest) {
        Long timingId = WorkflowUtility.getNextTimingId(this.officeTimingRepository.getTimingId());
        MOfficeTimings mofficeTiminges = getMOfficeTimingesObject(officeTimingRequest);
        mofficeTiminges.setTimingId(timingId);
        this.officeTimingRepository.saveAndFlush(mofficeTiminges);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Timing added successfully with Timing Id : " + mofficeTiminges.getTimingId());
    }

    public ResponseEntity<?> getLastTimingForAll() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(WorkflowUtility.listTimingResultToMap(this.officeTimingRepository.findUsingLastofAll()));
    }

    public ResponseEntity<?> getTimingsForProvince(Long provinceId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(WorkflowUtility.listTimingResultToMap(officeTimingRepository.findByProvinceId(provinceId)));
    }

    private MOfficeTimings getMOfficeTimingesObject(Map<String, String> officeTimingRequest) {
        MOfficeTimings mofficeTiminges = new MOfficeTimings();
        if (officeTimingRequest.containsKey("description"))
            mofficeTiminges.setDescription(officeTimingRequest.get("description"));
        if (officeTimingRequest.containsKey("fromDate"))
            mofficeTiminges.setFromDate(getDateTime(officeTimingRequest.get("fromDate")));
        if (officeTimingRequest.containsKey("fromTime"))
            mofficeTiminges.setFromTime(getDateTime(officeTimingRequest.get("fromTime")));
        if (officeTimingRequest.containsKey("toDate"))
            mofficeTiminges.setToDate(getDateTime(officeTimingRequest.get("toDate")));
        if (officeTimingRequest.containsKey("toTime"))
            mofficeTiminges.setToTime(getDateTime(officeTimingRequest.get("toTime")));
        if (officeTimingRequest.containsKey("provinceId"))
            mofficeTiminges.setProvinceId(Long.valueOf(officeTimingRequest.get("provinceId")));
        if (officeTimingRequest.containsKey("status"))
            mofficeTiminges.setStatus(Long.valueOf(officeTimingRequest.get("status")));
        return mofficeTiminges;
    }

    private LocalDateTime getDateTime(String str) {
        if (null == str)
            return null;

        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(str);
        return LocalDateTime.parse(str, dateTimeFormatter);
    }
}
