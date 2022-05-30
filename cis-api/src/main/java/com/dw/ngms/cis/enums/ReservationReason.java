package com.dw.ngms.cis.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.stream.Stream;

/**
 * @author prateek on 23-12-2021
 */
public enum ReservationReason {

    ANCILLARY(651L),
    CONSOLIDATION(597L),
    CREATION_OF_FARMS(602L),
    CREATION_OF_TOWN(601L),
    EXCISION(600L),
    EXTENSION_OF_TOWNSHIPS(599L),
    LEASE_ERVEN(648L),
    LEASES_AMENDING_GENERAL_PLANS(649L),
    PARTIAL_CANCELLATION_OF_GENERAL_PLAN(650L),
    PUBLIC_PLACE_CLOSURE(646L),
    RE_DESIGNATION(647L),
    STREET_CLOSURE(603L),
    RELAYOUT(1086L),
    PROCLAMATION(1087L),
    SUBDIVISION(596L);

    private final Long code;

    ReservationReason(final Long code) {
        this.code = code;
    }

    @JsonCreator
    public static ReservationReason of(final Long code) {
        return Stream.of(ReservationReason.values())
                .filter(requestType1 -> requestType1.getReservationReason().equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("invalid value"));
    }

    public Long getReservationReason() {
        return this.code;
    }


}
