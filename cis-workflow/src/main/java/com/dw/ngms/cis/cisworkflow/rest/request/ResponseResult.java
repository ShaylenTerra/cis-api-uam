package com.dw.ngms.cis.cisworkflow.rest.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author : prateekgoel
 * @since : 28/05/21, Fri
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
