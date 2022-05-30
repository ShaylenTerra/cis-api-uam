package com.dw.ngms.cis.service.mapper;

import com.dw.ngms.cis.persistence.domains.Template;
import com.dw.ngms.cis.persistence.domains.TemplatesAudit;
import com.dw.ngms.cis.service.UserService;
import com.dw.ngms.cis.service.dto.TemplateDto;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author : prateekgoel
 * @since : 20/11/20, Fri
 **/
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
@DecoratedWith(TemplateMapperDecorator.class)
public interface TemplateMapper {

    TemplateDto templateToTemplateDto(Template template);

    @InheritInverseConfiguration
    Template templateDtoTemplate(TemplateDto templateDto);

    TemplateDto templateAuditToTemplateDto(TemplatesAudit templatesAudit);

}
