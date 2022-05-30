package com.dw.ngms.cis.configuration;

import com.dw.ngms.cis.security.CustomUserDetailsContextMapperImpl;
import com.dw.ngms.cis.security.RestJwtAccessTokenConvertor;
import io.micrometer.core.instrument.util.IOUtils;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import java.io.IOException;
import java.security.KeyPair;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

/**
 * @author : prateekgoel
 * @since : 30/04/21, Fri
 **/
@Configuration
@AllArgsConstructor
@Profile(AppConstants.APP_PROFILE_INTERNAL)
@EnableWebSecurity
public class LdapSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String JWK_KID = "jwt-key-id";

    private final AppPropertiesConfig appPropertiesConfig;

    private final JdbcTemplate jdbcTemplate;

    private final CustomUserDetailsContextMapperImpl customUserDetailsContextMapper;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Order(Ordered.HIGHEST_PRECEDENCE)
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers(appPropertiesConfig.getSecurity().getExcludedOauthUrl()).permitAll()
                .and()
                .httpBasic()
                .realmName(appPropertiesConfig.getJwt().getSecurityRealm());
    }

    @Bean(name = "authorizationServerAccessTokenConverter")
    @Primary
    public JwtAccessTokenConverter authorizationServerAccessTokenConverter() {
        Map<String, String> customHeaders = Collections.singletonMap("kid", JWK_KID);
        return new RestJwtAccessTokenConvertor(customHeaders, keyPair());

    }

    @Bean
    public KeyPair keyPair() {
        KeyStoreKeyFactory keyStoreKeyFactory =
                new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "pass@123".toCharArray());
        return keyStoreKeyFactory.getKeyPair("jwt");
    }

    @Bean("resourceServerAccessTokenConverter")
    public JwtAccessTokenConverter resourceServerAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        Resource resource = new ClassPathResource("public.txt");
        String publicKey;
        try {
            publicKey = IOUtils.toString(resource.getInputStream());
        } catch (final IOException e) {
            throw new RuntimeException(e);
        }
        converter.setVerifierKey(publicKey);
        return converter;
    }


    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(Objects.requireNonNull(jdbcTemplate.getDataSource()));
    }

    @Bean
    @Primary
    //Making this primary to avoid any accidental duplication with another token service instance of the same name
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        final AppPropertiesConfig.Jwt jwt = appPropertiesConfig.getJwt();
        defaultTokenServices.setTokenStore(tokenStore());
        defaultTokenServices.setAccessTokenValiditySeconds(jwt.getAccessTokenValiditySeconds());
        defaultTokenServices.setRefreshTokenValiditySeconds(jwt.getRefreshTokenValiditySeconds());
        defaultTokenServices.setSupportRefreshToken(true);
        return defaultTokenServices;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        final AppPropertiesConfig.Ldap ldap = appPropertiesConfig.getLdap();
        auth.ldapAuthentication()
                .userSearchBase(ldap.getUserSearchBase())
                .userSearchFilter(ldap.getUserSearchFilter())
                .groupSearchBase(ldap.getGroupSearchBase())
                .contextSource(ldapContextSource())
                .userDetailsContextMapper(customUserDetailsContextMapper);
    }

    @Bean
    public LdapContextSource ldapContextSource() {
        final AppPropertiesConfig.Ldap ldap = appPropertiesConfig.getLdap();
        LdapContextSource ldapContextSource = new LdapContextSource();
        ldapContextSource.setUrl(ldap.getUrl());
        ldapContextSource.setUserDn(ldap.getUserDn());
        ldapContextSource.setPassword(ldap.getPassword());
        ldapContextSource.setPooled(Boolean.TRUE);
        ldapContextSource.setReferral(ldap.getReferral());
        return ldapContextSource;
    }
}
