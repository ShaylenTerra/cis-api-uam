package com.dw.ngms.cis.enums;

import java.util.stream.Stream;

/**
 * @author : prateekgoel
 * @since : 12/01/21, Tue
 **/
public enum SearchContext {

    NUMBER("NUM"),
    PARCEL_DESCRIPTION("PAR"),
    SECTIONAL_TITLE("SEC"),
    TEMPLATE_SEARCH("TEMP"),
    RANGE_SEARCH("RANGE"),
    TEXT_SEARCH("TXT"),
    NGI("NGI_DATA");

    final String msg;

    SearchContext(String msg) {
        this.msg = msg;
    }

    public static SearchContext of(String msg) {
        return Stream.of(SearchContext.values())
                .filter(searchContext -> searchContext.getSearchContext().equals(msg))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public String getSearchContext() {
        return msg;
    }

}
