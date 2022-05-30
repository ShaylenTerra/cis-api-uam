package com.dw.ngms.cis.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : prateekgoel
 * @since : 18/02/21, Thu
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class UserException extends RuntimeException {

    public UserException(String message) {
        super(message);
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }
}
