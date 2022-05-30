package com.dw.ngms.cis.enums;

import java.util.stream.Stream;

/**
 * @author : prateekgoel
 * @since : 28/05/21, Fri
 **/
public enum SystemConfigurationType {

    PASSWORD_EXPIRED_POLICY("PASSWORD_EXPIRED_POLICY"),
    SYSTEM_CASHIER("SYSTEM_CASHIER"),
    EMAIL_ATTACHMENT("EMAIL_ATTACHMENT"),
    IMAGE_DOWNLOAD_MB("IMAGE_DOWNLOAD_MB"),
    INVOICE_PAYMENT_DUE_DAYS("INVOICE_PAYMENT_DUE_DAYS");

    private final String tag;

    SystemConfigurationType(String tag) {
        this.tag = tag;
    }

    public static SystemConfigurationType of(String tag) {
        return Stream.of(SystemConfigurationType.values())
                .filter(u -> u.getSystemConfigurationType().equals(tag))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public String getSystemConfigurationType() {
        return tag;
    }
}
