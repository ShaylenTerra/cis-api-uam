package com.dw.ngms.cis.configuration.apidoc;

import com.dw.ngms.cis.configuration.AppConstants;
import com.dw.ngms.cis.configuration.AppPropertiesConfig;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RestController;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.AuthorizationCodeGrantBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

/**
 * @author : prateekgoel
 * @since : 21/11/20, Sat
 * <p>
 * Springfox Swagger configuration.
 * Warning! When having a lot of REST endpoints, Springfox can become a performance issue. In that
 * case, you can use a specific Spring profile for this class, so that only front-end developers
 * have access to the Swagger view.
 **/
@Configuration
@EnableSwagger2
@AllArgsConstructor
@Slf4j
@Import(BeanValidatorPluginsConfiguration.class)
@Profile({AppConstants.SPRING_PROFILE_DEVELOPMENT})
public class SpringfoxConfiguration {

    private final AppPropertiesConfig appPropertiesConfig;

    @Bean
    public Docket api(AppPropertiesConfig appPropertiesConfig) {
        log.debug("Starting Swagger");
        StopWatch watch = new StopWatch();
        watch.start();
        ApiInfo apiInfo = new ApiInfo(
                appPropertiesConfig.getSwagger().getTitle(),
                appPropertiesConfig.getSwagger().getDescription(),
                appPropertiesConfig.getSwagger().getVersion(),
                appPropertiesConfig.getSwagger().getTermsOfServiceUrl(),
                new Contact(appPropertiesConfig.getSwagger().getContact(), "www.cis.com", "noreply@harvesting.co"),
                appPropertiesConfig.getSwagger().getLicense(),
                appPropertiesConfig.getSwagger().getLicenseUrl(),
                Collections.emptyList());

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .genericModelSubstitutes(ResponseEntity.class)
                .forCodeGeneration(Boolean.TRUE)
                .directModelSubstitute(LocalDate.class, String.class)
                .directModelSubstitute(LocalTime.class, String.class)
                .directModelSubstitute(ZonedDateTime.class, Date.class)
                .directModelSubstitute(LocalDateTime.class, Date.class)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Collections.singletonList(securityScheme()))
                .securityContexts(Collections.singletonList(securityContext()));

        watch.stop();
        log.debug("Started Swagger in {} ms", watch.getTotalTimeMillis());
        return docket;
    }

    @Bean
    public SecurityConfiguration securityConfiguration() {
        return SecurityConfigurationBuilder.builder()
                .clientId(appPropertiesConfig.getSwagger().getClientId())
                .clientSecret(appPropertiesConfig.getSwagger().getClientSecret())
                .realm("")
                .appName("cis-api")
                .scopeSeparator(" ")
                .useBasicAuthenticationWithAccessCodeGrant(false)
                .build();
    }

    private SecurityScheme securityScheme() {
        GrantType grantType = new AuthorizationCodeGrantBuilder()
                .tokenEndpoint(new TokenEndpoint(appPropertiesConfig.getSwagger().getAuthServerUrl()
                        + "/token", "oauthtoken"))
                .tokenRequestEndpoint(
                        new TokenRequestEndpoint(appPropertiesConfig.getSwagger().getAuthServerUrl()
                                + "/authorize", appPropertiesConfig.getSwagger().getClientId(),
                                appPropertiesConfig.getSwagger().getClientSecret()))
                .build();

        return new OAuthBuilder().name("cis_oauth")
                .grantTypes(Collections.singletonList(grantType))
                .scopes(Arrays.asList(scopes()))
                .build();
    }

    private AuthorizationScope[] scopes() {
        return new AuthorizationScope[]{
                new AuthorizationScope("read", "for read operations"),
                new AuthorizationScope("write", "for write operations"),
                new AuthorizationScope("cis", "Access cis API")};
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(
                        Collections.singletonList(new SecurityReference("cis_oauth", scopes())))
                .forPaths(PathSelectors.regex(AppConstants.API_BASE_MAPPING + ".*"))
                .build();
    }

}
