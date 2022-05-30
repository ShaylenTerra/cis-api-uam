package com.dw.ngms.cis.web.rest;

import com.dw.ngms.cis.enums.ReservationSubType;
import com.dw.ngms.cis.service.reservation.ReservationSimulatorService;
import com.dw.ngms.cis.web.request.ReservationSimulatorRequest;
import com.dw.ngms.cis.web.response.ReservationSimulatorResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles({"internal","dev"})
class ReservationSimulatorResourceTest {

    @Autowired
    private ReservationSimulatorService reservationSimulatorService;



    @Test
    void processReservation() {
        ReservationSimulatorRequest reservationSimulatorRequest = new ReservationSimulatorRequest();
//        reservationSimulatorRequest.setReservationReason(2L);
//        reservationSimulatorRequest.setReservationType(626L);
        reservationSimulatorRequest.setProvinceId(6L);
        reservationSimulatorRequest.setNoOfParcel(5L);
        reservationSimulatorRequest.setLocationId(4383L);
        reservationSimulatorRequest.setReservationSubType(ReservationSubType.ERF);
        ReservationSimulatorResponse process = reservationSimulatorService.process(reservationSimulatorRequest);
        Assertions.assertThat(process).isNotNull();
    }
}