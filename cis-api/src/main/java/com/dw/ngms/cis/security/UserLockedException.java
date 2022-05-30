package com.dw.ngms.cis.security;

import com.dw.ngms.cis.enums.ResponseCode;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * @author : prateekgoel
 * @since : 21/05/21, Fri
 **/
public class UserLockedException extends OAuth2Exception {

    public static final String LOCKED_USER_ERROR = "locked_user_error";

    public UserLockedException(String msg, Throwable t) {
        super(msg, t);
    }

    public UserLockedException(String msg) {
        super(msg);
    }

    public String getOAuth2ErrorCode() {
        return LOCKED_USER_ERROR;
    }

    public int getHttpErrorCode() {
        return ResponseCode.LOCKED_USER_ERROR.getCode();
    }
}
