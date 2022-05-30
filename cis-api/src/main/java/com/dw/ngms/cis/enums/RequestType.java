package com.dw.ngms.cis.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author : prateekgoel
 * @since : 30/05/21, Sun
 **/
public enum RequestType {

    ANONYMOUS(439L),

    EMAIL(440L),

    FAX(441L),

    POST(442L),

    COURIER(443L),

    SMS(444L),

    TELEPHONE(445L),

    INTERNAL_REQUEST(446L),

    SELF(447L);

    private Long requestType;

    RequestType(Long requestType) {
        this.requestType = requestType;
    }

    public Long getRequestType(){
        return this.requestType;
    }

    @JsonCreator
    public static RequestType of(final Long value){
        return Stream.of(RequestType.values())
                .filter(requestType1 -> requestType1.getRequestType().equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("invalid value"));
    }

}
