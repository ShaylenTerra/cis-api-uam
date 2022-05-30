package com.dw.ngms.cis.security;

import org.springframework.security.core.Authentication;

/**
 * @author : prateekgoel
 * @since : 28/04/21, Wed
 **/
public interface UserSecurity {

    Authentication getAuthentication();

}
