package com.dw.ngms.cis.cisworkflow.service;

import com.dw.ngms.cis.cisworkflow.persistence.domain.WorkflowProcessMatrix;
import com.dw.ngms.cis.cisworkflow.persistence.repository.WorkflowProcessMatrixRepository;
import com.dw.ngms.cis.cisworkflow.utility.WorkflowUtility;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author : prateekgoel
 * @since : 20/05/21, Thu
 **/
@Service
@AllArgsConstructor
public class WorkflowMatrixService {


    private final WorkflowProcessMatrixRepository workflowprocessMatrixRepository;

    public ResponseEntity<?> addItem(Map<String, Object> requestMatrix) {
        WorkflowProcessMatrix workflowprocessMatrix = new WorkflowProcessMatrix();
        workflowprocessMatrix.setIsActive(1L);
        workflowprocessMatrix.setDescription((String) requestMatrix.get("description"));
        workflowprocessMatrix.setTag((String) requestMatrix.get("tag"));
        workflowprocessMatrix.setTypeflag((String) requestMatrix.get("typeflag"));
        workflowprocessMatrix.setMatrixId(workflowprocessMatrixRepository.getNextId());
        this.workflowprocessMatrixRepository.saveAndFlush(workflowprocessMatrix);
        return ResponseEntity.ok("success");
    }

    public ResponseEntity<?> performGetWorkflowProcessMatrixOperation() {
        List<WorkflowProcessMatrix> workflowprocessMatrixList = this.workflowprocessMatrixRepository.findByIsActive(1L);
        return ResponseEntity.status(HttpStatus.OK).body(WorkflowUtility.matrixListToMatrixMap(workflowprocessMatrixList));
    }

}
