package com.dw.ngms.cis.exception;

/**
 * @author : prateekgoel
 * @since : 20/11/20, Fri
 **/
public class FileStorageException extends RuntimeException{

    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
