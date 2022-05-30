package com.dw.ngms.cis.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author : prateekgoel
 * @since : 18/06/21, Fri
 **/
@Configuration
@ConfigurationProperties(prefix = "cis-search", ignoreUnknownFields = false)
@Data
public class SearchProperties {

    ImagePrefixPath imagePrefixPath = new ImagePrefixPath();

    @Data
    public class ImagePrefixPath {
        private String thumbnailPrefix;
        private String urlPrefix;
        private String previewPrefix;
    }
}
