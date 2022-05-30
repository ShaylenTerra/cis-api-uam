package com.dw.ngms.cis.persistence.projection.report;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author : prateekgoel
 * @since : 15/06/21, Tue
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DisplayName {
    String value() default "";
}
