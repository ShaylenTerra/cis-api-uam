package com.dw.ngms.cis.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.stream.Stream;

/**
 * @author : prateekgoel
 * @since : 30/05/21, Sun
 **/
public enum DeliveryMethod {

    POST(168L),
    COURIER(79L),
    COLLECTION(80L),
    ELECTRONIC(206L);


    private final Long deliveryMethod;

    DeliveryMethod(Long deliveryMethod) {
        this.deliveryMethod = deliveryMethod;
    }

    public Long getDeliveryMethod() {
        return deliveryMethod;
    }

    @JsonCreator
    public static DeliveryMethod of(final Long value) {
        return Stream.of(DeliveryMethod.values())
                .filter(deliveryMethod1 -> deliveryMethod1.getDeliveryMethod().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(" Invalid Value"));
    }
}
