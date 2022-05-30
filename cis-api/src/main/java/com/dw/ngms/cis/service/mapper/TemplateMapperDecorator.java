package com.dw.ngms.cis.service.mapper;

import com.dw.ngms.cis.persistence.domains.TemplatesAudit;
import com.dw.ngms.cis.persistence.domains.User;
import com.dw.ngms.cis.persistence.repository.UserRepository;
import com.dw.ngms.cis.service.dto.TemplateDto;
import com.dw.ngms.cis.service.mapper.user.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class TemplateMapperDecorator implements TemplateMapper {

    @Autowired
    @Qualifier("delegate")
    private TemplateMapper delegate;

    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;

    @Override
    public TemplateDto templateAuditToTemplateDto(TemplatesAudit templatesAudit) {
        TemplateDto templateDto = delegate.templateAuditToTemplateDto(templatesAudit);

        Long userId = templatesAudit.getUserId();

        User userById = userRepository.getUserById(userId);

        if (null != userById)
            templateDto.setUserName(userById.getUserName());

        return templateDto;
    }
}
