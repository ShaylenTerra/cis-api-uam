package com.dw.ngms.cis.cisworkflow.exception;

import com.dw.ngms.cis.cisworkflow.rest.request.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

/**
 * @author : prateekgoel
 * @since : 28/05/21, Fri
 **/
@ControllerAdvice
@ResponseBody
@Slf4j
public class ExceptionHandlerAdvice {

    /**
     * Handling Uncaptured Exception
     *
     * @param e abnormal
     * @return Unified Responsor
     */
    @ExceptionHandler({Exception.class, RuntimeException.class,NullPointerException.class})
    public ResponseResult handleException(WebRequest request, Exception e) {
        log.error(e.getMessage(), e);
        return ResponseResult.builder()
                .code(50000)
                .msg("INTERNAL_SERVER_ERROR")
                .requestUri(request.getContextPath())
                .data(e.getMessage())
                .build();
    }

}
