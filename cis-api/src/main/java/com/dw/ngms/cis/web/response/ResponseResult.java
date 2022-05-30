package com.dw.ngms.cis.web.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author : prateekgoel
 * @since : 03/12/20, Thu
 *
 * Unified public responder
 **/
@Data
@Builder
@AllArgsConstructor
public class ResponseResult<T> implements Serializable {

    /**
     * Return status code
     */
    private Integer code;
    /**
     * server timestamp
     */
    private LocalDateTime timestamp;
    /**
     * Return information
     */
    private String msg;

    /**
     *  request uri
     */
    private String requestUri;
    /**
     * data
     */
    private T data;

}
