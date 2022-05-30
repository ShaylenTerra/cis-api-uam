package com.dw.ngms.cis.configuration.template;

import com.dw.ngms.cis.persistence.repository.TemplateRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

/**
 * @author : prateekgoel
 * @since : 20/11/20, Fri
 **/
@Configuration
public class TemplateConfiguration {

    public static final String TEMPLATE_ENCODING = "UTF-8";

    @Bean
    @Qualifier(value = "thymeleafDbEngine")
    public SpringTemplateEngine springTemplateEngine(TemplateRepository templateRepository) {
        SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
        springTemplateEngine.addDialect(new Java8TimeDialect());
        springTemplateEngine.addTemplateResolver(new DatabaseTemplateResolver(templateRepository));
        springTemplateEngine.addTemplateResolver(htmlTemplateResolver());
        return springTemplateEngine;
    }

    private ClassLoaderTemplateResolver htmlTemplateResolver() {
        final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("reports/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding(TEMPLATE_ENCODING);
        templateResolver.setCacheable(false);
        templateResolver.setOrder(1);
        templateResolver.setCheckExistence(true);
        return templateResolver;
    }

}
