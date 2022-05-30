package com.dw.ngms.cis.configuration;

import com.dw.ngms.cis.security.RestMethodSecurityExpressionHandler;
import com.dw.ngms.cis.security.RestPermissionEvaluator;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

/**
 * @author : prateekgoel
 * @since : 04/05/21, Tue
 **/
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true, securedEnabled = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        RestMethodSecurityExpressionHandler restMethodSecurityExpressionHandler =
                new RestMethodSecurityExpressionHandler();
        restMethodSecurityExpressionHandler.setPermissionEvaluator(new RestPermissionEvaluator());
        return restMethodSecurityExpressionHandler;
    }
}
