package com.dw.ngms.cis.web.response;

import com.dw.ngms.cis.service.dto.reservation.ParentParcel;
import com.dw.ngms.cis.service.dto.reservation.ReservationDetails;
import lombok.Data;

import java.util.Collection;

@Data
public class ReservationSimulatorResponse {

    private Collection<ParentParcel> parentParcels;

    private String algorithm;

    private Collection<ReservationDetails> reservationDetails;

}
