package com.dw.ngms.cis.security;

import com.dw.ngms.cis.configuration.AppConstants;
import com.dw.ngms.cis.configuration.AppPropertiesConfig;
import com.dw.ngms.cis.exception.RestAccessDeniedHandler;
import com.dw.ngms.cis.exception.RestAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

/**
 * @author : prateekgoel
 * @since : 26/04/21, Mon
 **/
@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private final ResourceServerTokenServices resourceServerTokenServices;

    private final JwtAccessTokenConverter accessTokenConverter;

    private final AppPropertiesConfig appPropertiesConfig;

    private final RestAuthenticationEntryPoint restAuthenticationEntryPoint;

    private final RestAccessDeniedHandler restAccessDeniedHandler;

    @Autowired
    public ResourceServerConfig(final ResourceServerTokenServices resourceServerTokenServices,
                                @Qualifier("resourceServerAccessTokenConverter") final JwtAccessTokenConverter accessTokenConverter,
                                AppPropertiesConfig appPropertiesConfig,
                                RestAuthenticationEntryPoint restAuthenticationEntryPoint,
                                RestAccessDeniedHandler restAccessDeniedHandler) {
        this.resourceServerTokenServices = resourceServerTokenServices;
        this.accessTokenConverter = accessTokenConverter;
        this.appPropertiesConfig = appPropertiesConfig;
        this.restAuthenticationEntryPoint = restAuthenticationEntryPoint;
        this.restAccessDeniedHandler = restAccessDeniedHandler;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.tokenServices(resourceServerTokenServices)
                .resourceId(appPropertiesConfig.getJwt().getSecurityRealm());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .requestMatchers()
                .and()
                .authorizeRequests()
                .antMatchers(appPropertiesConfig.getSecurity().getExcludedApiUrl()).permitAll()
                .antMatchers(AppConstants.API_BASE_MAPPING + "/**").authenticated()
                .and().exceptionHandling()
                .authenticationEntryPoint(restAuthenticationEntryPoint)
                .accessDeniedHandler(restAccessDeniedHandler);
        http.headers().frameOptions().disable();
        http.cors();
    }
}
