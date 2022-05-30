package com.dw.ngms.cis.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.stream.Stream;

public enum FeeSimulatorType {

    RESERVATION("RESERVATION"),
    INFORMATION_REQUEST("INFORMATION_REQUEST"),
    LODGEMENT("LODGEMENT"),
    NGI("NGI");

    private final String msg;


    FeeSimulatorType(String reservation) {
        this.msg = reservation;
    }

    public String getFeeSimulatorType() {
        return this.msg;
    }

    @JsonCreator
    public static FeeSimulatorType of(final String value){
        return Stream.of(FeeSimulatorType.values())
                .filter(feeSimulatorType -> feeSimulatorType.getFeeSimulatorType().equals(value))
                .findFirst()
                .orElseThrow(()-> new IllegalArgumentException("Invalid Argument"));
    }
}
