package com.dw.ngms.cis.security;

import com.dw.ngms.cis.configuration.AppConstants;
import com.dw.ngms.cis.exception.ResourceNotFoundException;
import com.dw.ngms.cis.utilities.Messages;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : prateekgoel
 * @since : 28/04/21, Wed
 **/
@RestController
@AllArgsConstructor
@RequestMapping(AppConstants.API_BASE_MAPPING + "/user")
public class RevokeTokenEndpoint {

    private final TokenStore tokenStore;
    private final CurrentLoggedInUser currentLoggedInUser;
    private final DefaultTokenServices tokenServices;
    private final Messages messages;

    @DeleteMapping("/logout")
    @ResponseBody
    public void revokeToken() {
        final Authentication authentication = currentLoggedInUser.getAuthentication();
        if (authentication instanceof OAuth2Authentication) {
            final OAuth2Authentication oauthAuthentication = ((OAuth2Authentication) authentication);
            final OAuth2AccessToken accessToken = tokenStore.getAccessToken(oauthAuthentication);
            if (null != accessToken) {
                final String token = accessToken.getValue();
                tokenServices.revokeToken(token);
            }
        }

        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new ResourceNotFoundException(messages.getMessage("RevokeTokenEndpoint.authorization"));
        }
    }
}
