package com.dw.ngms.cis.cisworkflow.rest.web;

import com.dw.ngms.cis.cisworkflow.service.OfficeTimingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author : prateekgoel
 * @since : 20/05/21, Thu
 **/
@RestController
@AllArgsConstructor
public class OfficeTimingController {


    private final OfficeTimingService officeTimingService;


    @PostMapping({"/addOfficeTiming"})
    public ResponseEntity<?> addOfficeTiming(@RequestBody Map<String, String> officeTimingRequest) {
        return this.officeTimingService.addOfficeTiming(officeTimingRequest);
    }


    @GetMapping({"/getTimingForProvince"})
    public ResponseEntity<?> getTimingForProvince(@RequestParam Long provinceId) {
        return this.officeTimingService.getTimingsForProvince(provinceId);
    }

    @GetMapping({"/getTimings"})
    public ResponseEntity<?> getTimings() {
        return this.officeTimingService.getLastTimingForAll();
    }
}
