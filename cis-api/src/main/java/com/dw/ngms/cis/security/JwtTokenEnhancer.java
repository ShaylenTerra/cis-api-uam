package com.dw.ngms.cis.security;

import com.dw.ngms.cis.persistence.domains.user.SecurityUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : prateekgoel
 * @since : 27/04/21, Tue
 **/
public class JwtTokenEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        final Authentication userAuthentication = authentication.getUserAuthentication();
        final Object principal = userAuthentication.getPrincipal();
        Map<String, Object> additionalInformation = new HashMap<>();
        if (principal instanceof SecurityUser) {
            final SecurityUser user = (SecurityUser) principal;
            additionalInformation.put("userId", user.getUserId());
            additionalInformation.put("email", user.getEmail());
            additionalInformation.put("provinceId", user.getProvinceId());
        }
        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInformation);
        return accessToken;
    }
}
