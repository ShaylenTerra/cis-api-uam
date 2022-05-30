package com.dw.ngms.cis.enums;

/**
 * @author : prateekgoel
 * @since : 14/01/21, Thu
 **/
public enum StorageContext {

    PRE_PACKAGE("PRE_PACKAGE"),
    WORKFLOW("WORKFLOW"),
    FEE_CONFIGURATION("FEE_CONFIGURATION"),
    NEW_LEAVE("NEW_LEAVE"),
    RESERVATION("RESERVATION"),
    RESERVATION_DRAFT("RESERVATION_DRAFT"),
    LODGEMENT_DRAFT("LODGEMENT_DRAFT"),
    LODGEMENT_DRAFT_PAYMENT("LODGEMENT_DRAFT_PAYMENT"),
    LODGEMENT_DRAFT_RESERVATION_DETAIL_DOC("LODGEMENT_DRAFT_RESERVATION_DETAIL_DOC"),
    USER("USER");

    private final String context;

    StorageContext(final String context) {
        this.context = context;
    }

    public String getStorageContext() {
        return this.context;
    }

}
