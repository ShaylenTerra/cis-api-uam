package com.dw.ngms.cis.security;

import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.JsonParser;
import org.springframework.security.oauth2.common.util.JsonParserFactory;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.Map;

/**
 * @author : prateekgoel
 * @since : 29/04/21, Thu
 **/
public class RestJwtAccessTokenConvertor extends
        JwtAccessTokenConverter {

    private final RsaSigner signer;
    private Map<String, String> customHeaders;
    private JsonParser objectMapper = JsonParserFactory.create();

    public RestJwtAccessTokenConvertor(Map<String, String> customHeaders, KeyPair keyPair) {
        super();
        super.setKeyPair(keyPair);
        this.signer = new RsaSigner((RSAPrivateKey) keyPair.getPrivate());
        this.customHeaders = customHeaders;
    }

    @Override
    protected String encode(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        String content;
        try {
            content = this.objectMapper.formatMap(getAccessTokenConverter().convertAccessToken(accessToken, authentication));
        } catch (Exception ex) {
            throw new IllegalStateException("Cannot convert access token to JSON", ex);
        }
        return JwtHelper.encode(content, this.signer, this.customHeaders)
                .getEncoded();
    }

}
