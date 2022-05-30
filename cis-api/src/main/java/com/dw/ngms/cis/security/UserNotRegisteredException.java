package com.dw.ngms.cis.security;

import com.dw.ngms.cis.enums.ResponseCode;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * @author : prateekgoel
 * @since : 30/04/21, Fri
 **/
public class UserNotRegisteredException extends OAuth2Exception {

    public static final String UNREGISTERED_USER = "unregistered_user_error";

    public UserNotRegisteredException(String msg) {
        super(msg);
    }

    /**
     * Constructs a <code>BadCredentialsException</code> with the specified message and
     * root cause.
     *
     * @param msg   the detail message
     * @param cause root cause
     */
    public UserNotRegisteredException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public String getOAuth2ErrorCode() {
        return UNREGISTERED_USER;
    }

    public int getHttpErrorCode() {
        return ResponseCode.USER_NOT_REGISTERED.getCode();
    }

}
