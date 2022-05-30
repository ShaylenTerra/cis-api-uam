package com.dw.ngms.cis.exception;

import com.dw.ngms.cis.enums.ResponseCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : prateekgoel
 * @since : 03/12/20, Thu
 **/
@Data
@EqualsAndHashCode(callSuper = true)
public class BaseException extends RuntimeException {

    private ResponseCode code;

    public BaseException(ResponseCode code) {
        this.code = code;
    }

    public BaseException(Throwable cause, ResponseCode code) {
        super(cause);
        this.code = code;
    }
}
