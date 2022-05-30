package com.dw.ngms.cis.security;

import com.dw.ngms.cis.persistence.domains.user.SecurityUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author : prateekgoel
 * @since : 28/04/21, Wed
 **/
@Component
public class CurrentLoggedInUser implements UserSecurity {

    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext()
                .getAuthentication();
    }

    public SecurityUser getUser() {
        final Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if (null != authentication) {
            return ((SecurityUser) authentication.getPrincipal());
        }
        return null;
    }
}
