package com.dw.ngms.cis.enums;

import java.util.stream.Stream;

/**
 * @author : prateekgoel
 * @since : 08/12/20, Tue
 **/
public enum UserTypes {
    INTERNAL("Internal"),
    EXTERNAL_OTHER("External- Other"),
    EXTERNAL_PLS("External- PLS"),
    EXTERNAL_ARCHITECT("External-Architect");

    private final String userTypes;

    UserTypes(final String userType) {
        this.userTypes = userType;
    }

    public static UserTypes of(String userType) {
        return Stream.of(UserTypes.values())
                .filter(u -> u.getUserTypes().equals(userType))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public String getUserTypes() {
        return userTypes;
    }


}
