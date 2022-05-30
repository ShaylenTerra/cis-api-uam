package com.dw.ngms.cis.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.stream.Stream;

public enum ReservationSubType {

    ERF(706L, "Erf"),
    ERF_PORTION(707L, "Erf"),
    ERF_REMAINDER(708L, "Erf"),
    CON_ERF(709L, "Erf"),
    CON_DIFF_ERF_DIFF_PORTION(710L, "Erf"),
    CON_ERF_WITH_DIFF_ERF_PORTION(711L, "Erf"),
    SUB_FARM(726L, " the farm No"),
    SUB_LEASE(810L,""),
    SUB_LEASE_ERF(811L,""),
    SUB_FARM_PORTION(727L,"Farm No"),
    SUB_LEASE_FARM(812L,""),
    CON_SAME_ERF_DIFF_PORTION(712L, "Erf"),
    LEASE_CREATION_ERF(826L, "Lease"),
    LEASE_CREATION_ERF_PORTION(827L, "Lease"),
    LEASE_CREATION_ERF_REMAINDER(828L, "Lease");

    private final Long reservationSubType;

    private final String faceValue;

    ReservationSubType(Long reservationSubType, String faceValue) {
        this.reservationSubType = reservationSubType;
        this.faceValue = faceValue;
    }

    public Long getReservationSubType() {
        return this.reservationSubType;
    }

    public String getReservationSubTypeFaceValue() {
        return this.faceValue;
    }

    @JsonCreator
    public static ReservationSubType of(Long reservationSubType) {
        return Stream.of(ReservationSubType.values())
                .filter(u -> u.getReservationSubType().equals(reservationSubType))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }


}
