package com.dw.ngms.cis.enums;

import java.util.stream.Stream;

public enum UserType {

    INTERNAL(3L), EXTERNAL(4L),
    PROFESSIONAL_LAND_SURVEYOR(5065L),
    ASSISTANT(406L),
    ARCHITECT(5066L);

    private final Long userType;

    UserType(Long userType) {
        this.userType = userType;
    }

    public static UserType of(Long userType) {
        return Stream.of(UserType.values())
                .filter(u -> u.getUserType().equals(userType))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public static UserType of(String userType) {
        return UserType.valueOf(userType);
    }

    public Long getUserType() {
        return userType;
    }
}
