package com.dw.ngms.cis.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : prateekgoel
 * @since : 03/12/20, Thu
 *
 * Unified Response Note <br/>
 * The Unified Responsor will not take effect until annotations are added.
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface BaseResponse {
}
