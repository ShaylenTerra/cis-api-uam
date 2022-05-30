package com.dw.ngms.cis.web.request;

import com.dw.ngms.cis.enums.ReservationReason;
import com.dw.ngms.cis.enums.ReservationSubType;
import com.dw.ngms.cis.enums.ReservationType;
import lombok.Data;

@Data
public class ReservationSimulatorRequest {

    private ReservationReason reservationReason;

    private ReservationType reservationType;

    private ReservationSubType reservationSubType;

    private Long noOfParcel;

    private Long locationId;

    private Long provinceId;

    private String parcel;

}
