package com.dw.ngms.cis.service;

import com.dw.ngms.cis.persistence.domains.Template;
import com.dw.ngms.cis.persistence.domains.TemplatesAudit;
import com.dw.ngms.cis.persistence.domains.user.SecurityUser;
import com.dw.ngms.cis.persistence.repository.TemplatesAuditRepository;
import com.dw.ngms.cis.persistence.repository.TemplateRepository;
import com.dw.ngms.cis.security.CurrentLoggedInUser;
import com.dw.ngms.cis.service.dto.TemplateDto;
import com.dw.ngms.cis.service.mapper.TemplateMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

/**
 * @author : Name
 * @since : 19/11/20, Thu
 **/
@Service
@Slf4j
@AllArgsConstructor
public class TemplateService {

    private final TemplateRepository templateRepository;

    private final TemplateMapper templateMapper;

    private final TemplatesAuditRepository templatesAuditRepository;

    private final CurrentLoggedInUser currentLoggedInUser;


    /**
     * this method fetches all templates saved in DB
     * without using any filter
     *
     * @return list of template
     */
    public Collection<TemplateDto> getAllTemplates(final Long itemIdModule) {
        return templateRepository.findByItemIdModule(itemIdModule).stream()
                .map(templateMapper::templateToTemplateDto).collect(Collectors.toList());

    }

    /**
     * this method is used to save templates
     *
     * @param templateDto {@link TemplateDto}
     * @return {@link TemplateDto}
     */
    public TemplateDto saveTemplate(final TemplateDto templateDto) {
        Template template = templateMapper.templateDtoTemplate(templateDto);
        SecurityUser user = currentLoggedInUser.getUser();
        template.setUserId(user.getUserId());
        template.setDated(LocalDateTime.now());
        return templateMapper
                .templateToTemplateDto(templateRepository.save(template));

    }

    /**
     * @param templateId templateId
     * @return {@link TemplateDto}
     */
    public TemplateDto getTemplateById(final Long templateId) {
        final Template byTemplateId = templateRepository.findByTemplateId(templateId);
        return templateMapper.templateToTemplateDto(byTemplateId);
    }

    /**
     * @param templateId templateId
     * @param pageable   {@link Pageable}
     * @return Page<TemplateDto>
     */
    public Page<TemplateDto> getTemplateAuditHistory(final Long templateId, final Pageable pageable) {
        return templatesAuditRepository.findAllByTemplateIdOrderByDatedDesc(templateId, pageable)
                .map(templateMapper::templateAuditToTemplateDto);

    }

}
