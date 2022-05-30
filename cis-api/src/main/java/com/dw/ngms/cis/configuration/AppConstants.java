package com.dw.ngms.cis.configuration;

/**
 * @author : prateekgoel
 * @since : 28/04/21, Wed
 **/
public final class AppConstants {

    public static final String API_BASE_MAPPING = "/api/v1";

    // Spring profile for development, production and "fast",
    public static final String SPRING_PROFILE_DEVELOPMENT = "dev";
    public static final String SPRING_PROFILE_PRODUCTION = "prod";
    public static final String SPRING_PROFILE_SIT = "sit";
    public static final String SPRING_PROFILE_UAT = "uat";
    public static final String SPRING_PROFILE_FAST = "fast";

    public static final String SYSTEM_ACCOUNT = "system";

    public static final String APP_PROFILE_INTERNAL = "internal";
    public static final String APP_PROFILE_EXTERNAL = "external";

    private AppConstants() {
    }

}
