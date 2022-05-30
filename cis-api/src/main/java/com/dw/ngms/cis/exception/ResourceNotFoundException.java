package com.dw.ngms.cis.exception;

/**
 * @author : prateekgoel
 * @since : 11/02/21, Thu
 **/
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
