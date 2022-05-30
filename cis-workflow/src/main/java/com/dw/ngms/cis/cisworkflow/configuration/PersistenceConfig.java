package com.dw.ngms.cis.cisworkflow.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author : prateekgoel
 * @since : 20/05/21, Thu
 **/
@Configuration
@EnableJpaRepositories(basePackages = "com.dw.ngms.cis.cisworkflow.persistence.repository")
@EnableTransactionManagement
public class PersistenceConfig {
}
