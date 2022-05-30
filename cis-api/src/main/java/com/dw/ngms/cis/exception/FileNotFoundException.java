package com.dw.ngms.cis.exception;

/**
 * @author : prateekgoel
 * @since : 20/11/20, Fri
 **/
public class FileNotFoundException extends RuntimeException {
    public FileNotFoundException(String message) {
        super(message);
    }

    public FileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
