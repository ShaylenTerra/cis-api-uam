package com.dw.ngms.cis.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author : prateekgoel
 * @since : 28/04/21, Wed
 **/
@Configuration
@EnableJpaRepositories(basePackages = "com.dw.ngms.cis.persistence.repository")
@EnableTransactionManagement
public class PersistenceConfig {
}
