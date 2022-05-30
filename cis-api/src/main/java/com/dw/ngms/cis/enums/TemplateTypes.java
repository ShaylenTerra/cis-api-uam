package com.dw.ngms.cis.enums;

/**
 * @author : prateekgoel
 * @since : 22/02/21, Mon
 **/
public enum TemplateTypes {

    SG_NUMBER("SG_NUMBER"),
    COMPILATION_NUMBER("COMPILATION_NUMBER"),
    SURVEY_RECORD("SURVEY_RECORD"),
    PARCEL_ERF("PARCEL_ERF");

    private final String msg;

    TemplateTypes(String msg) {
        this.msg = msg;
    }

    public String getTemplateType() {
        return this.msg;
    }
}
