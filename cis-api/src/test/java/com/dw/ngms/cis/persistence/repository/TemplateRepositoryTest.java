package com.dw.ngms.cis.persistence.repository;

import com.dw.ngms.cis.persistence.domains.Template;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

/**
 * @author : Name
 * @since : 19/11/20, Thu
 **/
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class TemplateRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private TemplateRepository templateRepository;

    private Template template;

    /**
     * method to prepare data to be needed by
     *  repository layer
     */
    @BeforeEach
    public void setup(){
        this.template = new Template();
        template.setEmailDetails(null);
        template.setIsActive(1);
        template.setItemIdModule(1L);
        template.setPdfDetails(null);
        template.setTemplateId(10L);
        template.setTemplateName("TEST_TEMPLATE");
        template.setSmsDetails(null);
    }

    @Test
    public void whenFindAll_thenReturnAllTemplates() {
        List<Template> allTemplates = templateRepository.findAll();
        Assertions.assertThat(allTemplates).isEmpty();

    }

    @Test
    public void whenSaveTemplate_thenReturnSavedTemplate(){
        Template savedTemplate = templateRepository.save(template);
        Assertions.assertThat(savedTemplate).isNotNull();
    }
}