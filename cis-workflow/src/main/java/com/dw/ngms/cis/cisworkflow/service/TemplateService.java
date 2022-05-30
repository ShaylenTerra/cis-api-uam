package com.dw.ngms.cis.cisworkflow.service;

import com.dw.ngms.cis.cisworkflow.persistence.domain.MTemplates;
import com.dw.ngms.cis.cisworkflow.persistence.repository.TemplatesRepository;
import com.dw.ngms.cis.cisworkflow.utility.WorkflowUtility;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : prateekgoel
 * @since : 20/05/21, Thu
 **/
@Service
@AllArgsConstructor
public class TemplateService {


    private final TemplatesRepository templatesRepository;

    public ResponseEntity<?> getTemplates() {
        List<MTemplates> templateList = templatesRepository.findByIsActive(1L);
        return ResponseEntity.status(HttpStatus.OK).body(WorkflowUtility.templateListToTemplateMap(templateList));
    }

}
