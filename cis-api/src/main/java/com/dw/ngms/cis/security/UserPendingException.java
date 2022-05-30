package com.dw.ngms.cis.security;

import com.dw.ngms.cis.enums.ResponseCode;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * @author : prateekgoel
 * @since : 21/05/21, Fri
 **/
public class UserPendingException extends OAuth2Exception {

    public static final String PENDING_USER_ERROR = "pending_user_error";

    public UserPendingException(String msg, Throwable t) {
        super(msg, t);
    }

    public UserPendingException(String msg) {
        super(msg);
    }

    public String getOAuth2ErrorCode() {
        return PENDING_USER_ERROR;
    }

    public int getHttpErrorCode() {
        return ResponseCode.PENDING_USER_ERROR.getCode();
    }
}
