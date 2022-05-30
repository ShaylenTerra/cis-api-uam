package com.dw.ngms.cis.web.rest;

import com.dw.ngms.cis.configuration.AppConstants;
import com.dw.ngms.cis.service.TemplateService;
import com.dw.ngms.cis.service.dto.TemplateDto;
import com.dw.ngms.cis.web.annotation.ApiPageable;
import com.dw.ngms.cis.web.annotation.BaseResponse;
import com.dw.ngms.cis.web.util.PaginationUtil;
import com.dw.ngms.cis.web.views.TemplateDtoView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

/**
 * @author : Name
 * @since : 19/11/20, Thu
 **/
@BaseResponse
@RestController
@AllArgsConstructor
@RequestMapping(AppConstants.API_BASE_MAPPING + "/template")
public class TemplateResources {


    private final TemplateService templateService;


    /**
     * :POST this method takes template as a request body and provide saved template
     * in response.
     *
     * @param templateDto template data send as a rest api
     * @return saved template in database
     */
    @JsonView(TemplateDtoView.Normal.class)
    @PostMapping(value = "/add")
    public ResponseEntity<TemplateDto> saveTemplate(@RequestBody @Valid @JsonView(TemplateDtoView.Normal.class)
                                                                TemplateDto templateDto) {
        return ResponseEntity.ok()
                .body(templateService.saveTemplate(templateDto));
    }

    /**
     * :GET this method is simply fetch all templates from DB without any filter.
     *
     * @return response entity for collection of templateDto {@link TemplateDto}
     */
    @JsonView(TemplateDtoView.Normal.class)
    @GetMapping(value = "/list")
    public ResponseEntity<Collection<TemplateDto>>
    getAllTemplates(@RequestParam @NotNull final Long itemIdModule) {
        return ResponseEntity.ok()
                .body(templateService.getAllTemplates(itemIdModule));
    }

    /**
     * this method helps in update of template content based on templateId
     *
     * @param templateDto templateDto getting as part of request body
     * @return Boolean value whether template is updated or not
     */
    @JsonView(TemplateDtoView.Normal.class)
    @PostMapping(value = "/update")
    public ResponseEntity<TemplateDto> updateTemplate(@RequestBody @Valid @JsonView(TemplateDtoView.Normal.class)
                                                                  TemplateDto templateDto) {
        return ResponseEntity.ok()
                .body(templateService.saveTemplate(templateDto));

    }

    /**
     * @param templateId templateId
     * @return {@link TemplateDto}
     */
    @JsonView(TemplateDtoView.Normal.class)
    @GetMapping("/{templateId}")
    public ResponseEntity<TemplateDto> getTemplateById(@PathVariable final Long templateId) {
        return ResponseEntity.ok()
                .body(templateService.getTemplateById(templateId));
    }

    /**
     * @param templateId templateId
     * @param pageable   {@link Pageable}
     * @return Collection<TemplateDto>
     */
    @ApiPageable
    @GetMapping("/audit/{templateId}")
    @JsonView(TemplateDtoView.Audit.class)
    public ResponseEntity<Collection<TemplateDto>> getTemplateHistory(@PathVariable @NotNull final Long templateId,
                                                                      @ApiIgnore final Pageable pageable) {
        Page<TemplateDto> templateAuditHistory = templateService.getTemplateAuditHistory(templateId, pageable);
        HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(templateAuditHistory);
        return ResponseEntity.ok()
                .headers(httpHeaders)
                .body(templateAuditHistory.getContent());
    }
}
