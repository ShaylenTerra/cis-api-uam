package com.dw.ngms.cis.enums;

import java.util.stream.Stream;

/**
 * @author : prateekgoel
 * @since : 31/05/21, Mon
 **/
public enum ProcessTemplateType {

    INVOICE(15L),
    PAYMENT_UPLOAD(268L),
    PAYMENT_CONFIRMATION(16L),
    QUERY(20L),
    NOTIFY_MANAGER(107L),
    INFORMATION_REQUEST(13L),
    UNKNOWN(21L),
    DISPATCH(17L),
    NOTIFY_MANAGER_OUTCOME(247L),
    AUTO_DISPATCH(170L),
    SHARE_INFORMATION(347L),
    QUERY_OUTCOME(19L),
    RES_NEW_REQUEST_NOTIFICATION(307L),
    RES_CERTIFICATE_OF_EXCISION(329L),
    RES_REQUEST_REJECTION_LETTER(308L),
    RES_EXPIRY_NOTIFICATION(327L),
    RES_NEW_REQUEST_RESUBMISSION(348L);

    private final Long templateType;

    ProcessTemplateType(Long templateType) {
        this.templateType = templateType;
    }

    public static ProcessTemplateType of(Long templateType) {
        return Stream.of(ProcessTemplateType.values())
                .filter(u -> u.getTemplateType().equals(templateType))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public Long getTemplateType() {
        return this.templateType;
    }
}
