package com.dw.ngms.cis.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author : prateekgoel
 * @since : 19/11/20, Thu
 * <p>
 * Properties specific to cis-uam application.
 * <p>
 * Properties are configured in the application.yaml file.
 **/
@Configuration
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
@Data
public class AppPropertiesConfig {

    private final Sms sms = new Sms();

    private final FileStorage fileStorage = new FileStorage();

    private final Swagger swagger = new Swagger();

    private final Ftp ftp = new Ftp();

    private final Security security = new Security();

    private final Jwt jwt = new Jwt();

    private final Ldap ldap = new Ldap();

    @Data
    public class Sms {
        private String baseUrl;
        private String username;
        private String password;
        private String sender;
    }

    @Data
    public class FileStorage {
        private String storageLocation;
    }

    @Data
    public class Swagger {

        private String title = "CIS API";

        private String description = "CIS API documentation";

        private String version = "0.0.1";

        private String termsOfServiceUrl;

        private String contact;

        private String license;

        private String licenseUrl;

        private String authServerUrl;

        private String clientId;

        private String clientSecret;

    }

    @Data
    public class Ftp {
        private String server;
        private String user;
        private String password;
        private int port;
    }

    @Data
    public class Security {
        private String[] excludedApiUrl;
        private String[] excludedOauthUrl;

    }

    @Data
    public class Jwt {
        private String securityRealm;
        private int accessTokenValiditySeconds;
        private int refreshTokenValiditySeconds;
    }

    @Data
    public class Ldap {
        private String userSearchFilter;
        private String userSearchBase;
        private String groupSearchBase;
        private String url;
        private String userDn;
        private String referral;
        private String password;
    }

}
