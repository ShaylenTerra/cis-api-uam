package com.dw.ngms.cis.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.data.jdbc.JdbcRepositoriesAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author : prateekgoel
 * @since : 26/04/21, Mon
 **/
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final TokenStore tokenStore;

    private final JwtAccessTokenConverter accessTokenConverter;

    private final AuthenticationManager authenticationManager;

    private final UserDetailsService userDetailsService;

    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public AuthorizationServerConfig(final TokenStore tokenStore,
                                     @Qualifier("authorizationServerAccessTokenConverter") final JwtAccessTokenConverter accessTokenConverter,
                                     final AuthenticationManager authenticationManager,
                                     final UserDetailsService userDetailsService,
                                     final JdbcTemplate jdbcTemplate) {
        this.tokenStore = tokenStore;
        this.accessTokenConverter = accessTokenConverter;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.jdbc(jdbcTemplate.getDataSource());
    }




    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(customTokenEnhancer(), accessTokenConverter));
        endpoints.tokenStore(tokenStore)
                .reuseRefreshTokens(false)
                .tokenEnhancer(tokenEnhancerChain)
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService)
                .approvalStore(approvalStore())
                .authorizationCodeServices(authorizationCodeServices())
                .exceptionTranslator(oauth2ResponseExceptionTranslator());
    }

    @Bean
    public ApprovalStore approvalStore(){
        return new JdbcApprovalStore(jdbcTemplate.getDataSource());
    }

    @Bean
    protected AuthorizationCodeServices authorizationCodeServices(){
        return new JdbcAuthorizationCodeServices(jdbcTemplate.getDataSource());
    }

    @Bean
    public JwtTokenEnhancer customTokenEnhancer() {
        return new JwtTokenEnhancer();
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.authenticationEventPublisher(new NullEventPublisher())
                .tokenKeyAccess("hasAuthority('ROLE_TRUSTED_CLIENT')")
                .checkTokenAccess("hasAuthority('ROLE_TRUSTED_CLIENT')")
                .addTokenEndpointAuthenticationFilter(corsFilter());
    }


    @Bean
    public WebResponseExceptionTranslator<OAuth2Exception> oauth2ResponseExceptionTranslator() {
        return new DefaultWebResponseExceptionTranslator() {
            @Override
            public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
                final ResponseEntity<OAuth2Exception> responseEntity = super.translate(e);
                final OAuth2Exception body = responseEntity.getBody();
                final HttpStatus statusCode = responseEntity.getStatusCode();
                if (null != body) {
                    body.addAdditionalInformation("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME));
                    body.addAdditionalInformation("status", body.getHttpErrorCode() + "");
                    body.addAdditionalInformation("message", body.getMessage());
                    body.addAdditionalInformation("code", body.getOAuth2ErrorCode().toUpperCase());
                }
                HttpHeaders headers = new HttpHeaders();
                headers.setAll(responseEntity.getHeaders().toSingleValueMap());
                // do something with header or response
                return new ResponseEntity<>(body, headers, statusCode);
            }
        };
    }

    @Bean
    public CorsFilter corsFilter() {
        return new CorsFilter(corsConfigurationSource());
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowCredentials(true);
        configuration.setExposedHeaders(Arrays.asList("X-Total-Count", "X-Total-File-Size"));
        configuration.setAllowedHeaders(Arrays.asList(
                "Accept", "Origin", "Content-Type", "Depth", "User-Agent", "If-Modified-Since,",
                "Cache-Control", "Authorization", "X-Req", "X-File-Size", "X-Requested-With", "X-File-Name"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private static final class NullEventPublisher implements AuthenticationEventPublisher {
        public void publishAuthenticationFailure(AuthenticationException exception, Authentication authentication) {
        }

        public void publishAuthenticationSuccess(Authentication authentication) {
        }
    }
}
