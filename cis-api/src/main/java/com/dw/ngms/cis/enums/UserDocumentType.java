package com.dw.ngms.cis.enums;

import java.util.stream.Stream;

/**
 * @author : prateekgoel
 * @since : 08/05/21, Sat
 **/
public enum UserDocumentType {

    USER_LEAVE_CALENDAR(366L),
    USER_REGISTRATION_DOCUMENT(367L);

    private Long userDocumentType;

    UserDocumentType(Long userDocumentType) {
        this.userDocumentType = userDocumentType;
    }

    public static UserDocumentType of(Long userDocumentType) {
        return Stream.of(UserDocumentType.values())
                .filter(UserDocumentType1 -> UserDocumentType1
                        .getDocumentType().equals(userDocumentType))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public Long getDocumentType() {
        return userDocumentType;
    }
}
