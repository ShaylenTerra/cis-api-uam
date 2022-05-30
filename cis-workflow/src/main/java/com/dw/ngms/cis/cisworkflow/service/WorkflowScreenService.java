package com.dw.ngms.cis.cisworkflow.service;

import com.dw.ngms.cis.cisworkflow.persistence.domain.WorkflowProcessScreen;
import com.dw.ngms.cis.cisworkflow.persistence.repository.WorkflowProcessScreenRepository;
import com.dw.ngms.cis.cisworkflow.utility.WorkflowUtility;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author : prateekgoel
 * @since : 20/05/21, Thu
 **/
@Service
@Slf4j
@AllArgsConstructor
public class WorkflowScreenService {


    private final WorkflowProcessScreenRepository wrokflowprocessScreenRepository;

    public ResponseEntity<?> performGetWorkflowProcessScreenOperation() {
        List<WorkflowProcessScreen> workflowprocessScreenList = this.wrokflowprocessScreenRepository.findByIsActive(1L);
        return ResponseEntity.status(HttpStatus.OK).body(WorkflowUtility.screenListToScreenMap(workflowprocessScreenList));
    }
}
