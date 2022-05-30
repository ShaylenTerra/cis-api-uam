package com.dw.ngms.cis.enums;

import java.util.stream.Stream;

/**
 * @author : prateekgoel
 * @since : 05/12/20, Sat
 **/
public enum OfficeTimingType {

    OFFICE_HOLIDAY("OFFICE_HOLIDAY"),
    OFFICE_TIMING("OFFICE_TIMING");

    private final String msg;

    OfficeTimingType(String msg) {
        this.msg = msg;
    }

    public static OfficeTimingType of(String officeTimingType) {
        return Stream.of(OfficeTimingType.values())
                .filter(officeTimingType1 -> officeTimingType1
                        .getOfficeTimingType().equals(officeTimingType))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public String getOfficeTimingType() {
        return msg;
    }
}
