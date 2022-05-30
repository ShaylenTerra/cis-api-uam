package com.dw.ngms.cis.cisworkflow;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author prateekgoel
 * @since 19/11/20 7:46 AM
 * <p>
 * This is a helper Java class that provides an alternative to creating a {@code web.xml}.
 * This will be invoked only when the application is deployed to a Servlet container like Tomcat, JBoss etc.
 */
public class ApplicationWebXml extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CisWorkflowApplication.class);
    }
}
