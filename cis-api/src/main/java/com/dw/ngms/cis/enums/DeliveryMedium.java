package com.dw.ngms.cis.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.stream.Stream;

/**
 * @author : prateekgoel
 * @since : 30/05/21, Sun
 **/
public enum DeliveryMedium {

    FTP(166L),
    EMAIL(186L),
    CD(91L),
    DVD(92L),
    HDD(93L),
    FLASH_DRIVE(94L),
    UNKNOWN(167L),
    TELLEPOTATION(267L);


    private final Long deliveryMedium;

    DeliveryMedium(Long deliveryMedium) {
        this.deliveryMedium = deliveryMedium;
    }

    public Long getDeliveryMedium() {
        return this.deliveryMedium;
    }

    @JsonCreator
    public static DeliveryMedium of(final Long value){
        return Stream.of(DeliveryMedium.values())
                .filter(deliveryMedium1 -> deliveryMedium1.getDeliveryMedium().equals(value))
                .findFirst()
                .orElseThrow(()-> new IllegalArgumentException("Invalid Argument"));
    }
}
