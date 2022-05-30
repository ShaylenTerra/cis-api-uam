package com.dw.ngms.cis.security;

import com.dw.ngms.cis.persistence.domains.user.SecurityUser;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;

/**
 * @author : prateekgoel
 * @since : 04/05/21, Tue
 **/
public class RestMethodSecurityExpressionRoot extends SecurityExpressionRoot
        implements MethodSecurityExpressionOperations {

    private Object filterObject;
    private Object returnObject;


    public RestMethodSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    @Override
    public void setDefaultRolePrefix(String defaultRolePrefix) {
        super.setDefaultRolePrefix("");
    }

    public boolean isMember(Long userType) {
        final SecurityUser principal = (SecurityUser) this.getPrincipal();
        return principal.getUserTypeItemId().getUserType().longValue() == userType;
    }

    @Override
    public Object getFilterObject() {
        return this.filterObject;
    }

    @Override
    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    @Override
    public Object getReturnObject() {
        return this.returnObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getThis() {
        return this;
    }
}
