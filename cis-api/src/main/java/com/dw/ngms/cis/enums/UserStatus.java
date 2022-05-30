package com.dw.ngms.cis.enums;

public enum UserStatus {

    ACTIVE(108L),
    APPROVED(106L),
    PENDING(107L),
    INACTIVE(109L),
    REJECTED(115L),
    UNLOCK(122L),
    UNLINK(428L),
    LOCK(116L);


    private Long userStatus;

    UserStatus(Long userStatus) {
        this.userStatus = userStatus;
    }

    public Long getUserStatus() {
        return userStatus;
    }

}
