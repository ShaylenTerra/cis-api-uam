package com.dw.ngms.cis.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.stream.Stream;

/**
 * @author prateek on 21-12-2021
 */
public enum ReservationType {

    ERF(626L),
    LEASE(627L),
    FARM(628L);

    private final Long reservationType;

    ReservationType (Long reservationType) {
        this.reservationType = reservationType;
    }

    public Long getReservationType() {
        return this.reservationType;
    }

    @JsonCreator
    public static ReservationType of(Long reservationType) {
        return Stream.of(ReservationType.values())
                .filter(u -> u.getReservationType().equals(reservationType))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}
