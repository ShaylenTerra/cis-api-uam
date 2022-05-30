package com.dw.ngms.cis.configuration.template;

import com.dw.ngms.cis.persistence.domains.Template;
import com.dw.ngms.cis.persistence.repository.TemplateRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.thymeleaf.IEngineConfiguration;
import org.thymeleaf.templateresolver.StringTemplateResolver;
import org.thymeleaf.templateresource.ITemplateResource;

import java.util.Map;

/**
 * @author : prateekgoel
 * @since : 20/11/20, Fri
 **/
@Component
@Slf4j
@NoArgsConstructor
public class DatabaseTemplateResolver extends StringTemplateResolver {

    private TemplateRepository templateRepository;

    public DatabaseTemplateResolver(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
        this.setCacheTTLMs(5 * 60 * 1000L);
        this.setCacheable(true);
        this.setOrder(2);
    }

    @Override
    protected ITemplateResource computeTemplateResource(IEngineConfiguration configuration, String ownerTemplate,
                                                        String templateName, Map<String, Object> templateResolutionAttributes) {
        log.info(" loading template named {} from DB ", templateName);
        Template template = null;
        try {
            template = templateRepository.findByTemplateName(templateName);
        } catch (Exception e) {
            log.error(" error occurred while getting template {}", templateName);
            e.printStackTrace();
        }

        if (template == null) {
            return null;
        }
        return super.computeTemplateResource(configuration, ownerTemplate, template.getPdfDetails(),
                templateResolutionAttributes);
    }
}
