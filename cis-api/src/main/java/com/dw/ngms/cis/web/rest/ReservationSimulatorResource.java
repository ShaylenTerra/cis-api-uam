package com.dw.ngms.cis.web.rest;

import com.dw.ngms.cis.configuration.AppConstants;
import com.dw.ngms.cis.persistence.domains.listmanagement.ListItem;
import com.dw.ngms.cis.persistence.projection.prepackage.PrepackageMajorRegionOrAdminDistrict;
import com.dw.ngms.cis.service.reservation.ReservationSimulatorService;
import com.dw.ngms.cis.web.annotation.BaseResponse;
import com.dw.ngms.cis.web.request.ReservationSimulatorRequest;
import com.dw.ngms.cis.web.response.ReservationSimulatorResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@BaseResponse
@RestController
@AllArgsConstructor
@RequestMapping(AppConstants.API_BASE_MAPPING + "/reservation")
public class ReservationSimulatorResource {

    private final ReservationSimulatorService reservationSimulatorService;

    /**
     * @param parentItemId parentItemId
     * @param listCode     listCode
     * @return Collection<ListItem>
     */
    @GetMapping("/getType")
    public ResponseEntity<Collection<ListItem>> getReservationType(@RequestParam final Long parentItemId,
                                                                   @RequestParam final Long listCode) {
        Collection<ListItem> reservationType = reservationSimulatorService.getReservationType(parentItemId, listCode);
        return ResponseEntity.ok(reservationType);
    }

    /**
     * @param listId listId
     * @param parentItemId parentItemId
     * @return Collection<ListItem>
     */
    @GetMapping("/getSubType")
    public ResponseEntity<Collection<ListItem>> getReservationSubType(@RequestParam final Long listId,
                                                                      @RequestParam final Long parentItemId) {
        Collection<ListItem> reservationSubType = reservationSimulatorService
                .getReservationSubType(listId, parentItemId);
        return ResponseEntity.ok(reservationSubType);
    }

    /**
     * @param provinceId provinceId
     * @return Collection<PrepackageMajorRegionOrAdminDistrict>
     */
    @GetMapping("/getTownship")
    public ResponseEntity<Collection<PrepackageMajorRegionOrAdminDistrict>>
    getTownshipAllotment(@RequestParam final Long provinceId) {
        Collection<PrepackageMajorRegionOrAdminDistrict> townshipAllotment = reservationSimulatorService
                .getTownshipAllotment(provinceId);
        return ResponseEntity.ok(townshipAllotment);
    }

    /**
     * @param reservationSimulatorRequest reservationSimulatorRequest
     * @return ReservationSimulatorResponse
     */
    @PostMapping("/process")
    public ResponseEntity<ReservationSimulatorResponse>
    processReservation(@RequestBody final ReservationSimulatorRequest reservationSimulatorRequest) {
        ReservationSimulatorResponse process = reservationSimulatorService.process(reservationSimulatorRequest);
        return ResponseEntity.ok(process);
    }
}
