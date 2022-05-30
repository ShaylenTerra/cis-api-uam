package com.dw.ngms.cis.service.dto;

import com.dw.ngms.cis.web.views.TemplateDtoView;
import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * @author : prateekgoel
 * @since : 20/11/20, Fri
 **/
@Data
public class TemplateDto {

    @NotNull
    @JsonView(TemplateDtoView.Normal.class)
    private Long templateId;

    @NotNull
    @JsonView(TemplateDtoView.Normal.class)
    private String templateName;

    @JsonView(TemplateDtoView.Normal.class)
    private Long itemIdModule;

    @JsonView(TemplateDtoView.Normal.class)
    private Integer isActive;

    @JsonView(TemplateDtoView.Normal.class)
    private String pdfDetails;

    @JsonRawValue
    @JsonView(TemplateDtoView.Normal.class)
    private String emailDetails;

    @JsonRawValue
    @JsonView(TemplateDtoView.Normal.class)
    private String smsDetails;

    @JsonView(TemplateDtoView.Audit.class)
    private String userName;

    @JsonView(TemplateDtoView.Audit.class)
    private LocalDateTime dated;

}
