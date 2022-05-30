package com.dw.ngms.cis.exception;

/**
 * @author : prateekgoel
 * @since : 19/05/21, Wed
 **/
public class PasswordMismatchException extends UserException {

    public PasswordMismatchException(String message) {
        super(message);
    }

    public PasswordMismatchException(String message, Throwable cause) {
        super(message, cause);
    }
}
