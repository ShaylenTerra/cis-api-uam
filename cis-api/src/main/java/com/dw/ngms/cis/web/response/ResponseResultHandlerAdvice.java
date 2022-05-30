package com.dw.ngms.cis.web.response;

import com.dw.ngms.cis.enums.ResponseCode;
import com.dw.ngms.cis.web.annotation.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.time.LocalDateTime;

/**
 * @author : prateekgoel
 * @since : 03/12/20, Thu
 * <p>
 * Unified Responsor Processor
 **/
@ControllerAdvice(annotations = BaseResponse.class)
@Slf4j
public class ResponseResultHandlerAdvice implements ResponseBodyAdvice {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        log.info("returnType:" + returnType);
        log.info("converterType:" + converterType);
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType,
                                  MediaType selectedContentType,
                                  Class selectedConverterType,
                                  ServerHttpRequest request,
                                  ServerHttpResponse response) {
        if (MediaType.APPLICATION_JSON.equals(selectedContentType)) { // Judging that the Content-Type of the response is body in JSON format
            if (body instanceof ResponseResult) { // If the object returned by the response is a unified responder, the body is returned directly.
                return body;
            } else {
                // Only the normal returned result will enter the judgment process, so the normal successful status code will be returned.
                return ResponseResult.builder()
                        .code(ResponseCode.SUCCESS.getCode())
                        .timestamp(LocalDateTime.now())
                        .msg(ResponseCode.SUCCESS.getMsg())
                        .requestUri(request.getURI().getPath())
                        .data(body).build();
            }
        }
        // Non-JSON format body can be returned directly
        return body;
    }
}
