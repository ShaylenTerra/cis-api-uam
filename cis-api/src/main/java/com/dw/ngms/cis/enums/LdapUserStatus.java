package com.dw.ngms.cis.enums;

import java.util.stream.Stream;

/**
 * @author : prateekgoel
 * @since : 23/11/20, Mon
 **/
public enum LdapUserStatus {

    USER_VALID(1),
    USER_INVALID(-1),
    CAN_REGISTER(2),
    ALREADY_REGISTER(3);

    private Integer msg;


    LdapUserStatus(Integer valid) {
        this.msg = valid;
    }

    public static LdapUserStatus of(Integer ldapUserStatus) {
        return Stream.of(LdapUserStatus.values())
                .filter(luser -> luser.getLdapUserStatus().equals(ldapUserStatus))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

    public Integer getLdapUserStatus() {
        return msg;
    }
}
