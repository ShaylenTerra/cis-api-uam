package com.dw.ngms.cis.exception;

import com.dw.ngms.cis.enums.ResponseCode;
import com.dw.ngms.cis.web.annotation.BaseResponse;
import com.dw.ngms.cis.web.response.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : prateekgoel
 * @since : 03/12/20, Thu
 **/
@ControllerAdvice(annotations = BaseResponse.class)
@ResponseBody
@Slf4j
public class ExceptionHandlerAdvice {

    /**
     * Handling Uncaptured Exception
     * @param e abnormal
     * @return Unified Responsor
     */
    @ExceptionHandler(Exception.class)
    public ResponseResult handleException(WebRequest request, Exception e){
        log.error(e.getMessage(),e);
        return ResponseResult.builder()
                .code(ResponseCode.SERVICE_ERROR.getCode())
                .msg(ResponseCode.SERVICE_ERROR.getMsg())
                .requestUri(request.getContextPath())
                .data(e.getMessage())
                .build();
    }

    /**
     * Handling Uncaptured Runtime Exception
     * @param e Runtime exception
     * @return Unified Responsor
     */
    @ExceptionHandler({RuntimeException.class, ConversionFailedException.class})
    public ResponseResult handleRuntimeException(WebRequest request,RuntimeException e){
        log.error(e.getMessage(),e);
        return ResponseResult.builder()
                .code(ResponseCode.SERVICE_ERROR.getCode())
                .msg(ResponseCode.SERVICE_ERROR.getMsg())
                .requestUri(request.getContextPath())
                .data(e.getMessage())
                .build();
    }

    /**
     * Handling Business Exception
     *
     * @param e Business exceptions
     * @return Unified Responsor
     */
    @ExceptionHandler(BaseException.class)
    public ResponseResult handleBaseException(WebRequest request, BaseException e) {
        log.error(e.getMessage(), e);
        ResponseCode code = e.getCode();
        return ResponseResult.builder()
                .code(code.getCode())
                .msg(code.getMsg())
                .data(e.getMessage())
                .requestUri(request.getContextPath())
                .build();
    }

    /**
     * @param request web request
     * @param e       exception
     * @return Unified Responsor ResponseResult
     */
    @ExceptionHandler(UserException.class)
    public ResponseResult handleUserException(WebRequest request, UserException e) {
        log.error(e.getMessage(), e);
        return ResponseResult.builder()
                .code(ResponseCode.SERVICE_ERROR.getCode())
                .msg(e.getMessage())
                .data(e.getMessage())
                .requestUri(request.getContextPath())
                .build();
    }

    /**
     * @param request web request
     * @param e       exception
     * @return Unified Responsor ResponseResult
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseResult handleResourceNotFoundException(WebRequest request, ResourceNotFoundException e) {
        log.error(e.getMessage(), e);
        return ResponseResult.builder()
                .code(ResponseCode.SERVICE_ERROR.getCode())
                .msg(e.getMessage())
                .data(e.getMessage())
                .requestUri(request.getContextPath())
                .build();
    }

    /**
     * @param request web request
     * @param e       exception
     * @return Unified Responsor ResponseResult
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseResult handleException(WebRequest request, ConstraintViolationException e) {
        log.error(e.getMessage(), e);
        return ResponseResult.builder()
                .code(ResponseCode.SERVICE_ERROR.getCode())
                .msg(ResponseCode.SERVICE_ERROR.getMsg())
                .data(e.getMessage())
                .requestUri(request.getContextPath())
                .build();
    }

    /**
     * @param request web request
     * @param ex      exception
     * @return ResponseResult Unified Response
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseResult handleValidationExceptions(WebRequest request, MethodArgumentNotValidException ex) {
        Map<String, String> errors = extractError(ex);
        return ResponseResult.builder()
                .code(ResponseCode.BAD_REQUEST.getCode())
                .msg(ResponseCode.BAD_REQUEST.getMsg())
                .requestUri(request.getContextPath())
                .data(errors)
                .build();
    }

    /**
     * @param request web request
     * @param ex      exception
     * @return ResponseResult Unified Response
     */
    @ExceptionHandler(BindException.class)
    public ResponseResult handleBindException(WebRequest request, BindException ex) {
        final Map<String, String> errors = extractError(ex);
        return ResponseResult.builder()
                .code(ResponseCode.BAD_REQUEST.getCode())
                .msg(ResponseCode.BAD_REQUEST.getMsg())
                .requestUri(request.getContextPath())
                .data(errors)
                .build();
    }

    private Map<String, String> extractError(BindException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
