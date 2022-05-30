package com.dw.ngms.cis.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author : prateekgoel
 * @since : 19/11/20, Thu
 **/
@Configuration
public class ApplicationConfiguration {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
