package com.dw.ngms.cis.cisworkflow.rest.web;

import com.dw.ngms.cis.cisworkflow.rest.request.WorkflowProcessRequest;
import com.dw.ngms.cis.cisworkflow.service.WorkflowProcessingService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author : prateekgoel
 * @since : 20/05/21, Thu
 **/
@RestController
@AllArgsConstructor
public class RoutingController {


    private final WorkflowProcessingService workflowProcessingService;


    @PostMapping({"/triggertask"})
    public ResponseEntity<Map<String, Object>> triggerTask(@RequestBody Map<String, Object> workflowProcessingRequestParameter) throws Exception {
        return ResponseEntity.ok()
                .body(workflowProcessingService.triggerTask(workflowProcessingRequestParameter));
    }


    @GetMapping({"/getNodeDetails"})
    public ResponseEntity<String> getProcessNodeDetails(@RequestParam Long processId,
                                                        @RequestParam Long nodeId) throws Exception {
        return ResponseEntity.ok()
                .body(workflowProcessingService.getProcessNodeDetails(processId, nodeId));
    }

    @PostMapping({"/processtask"})
    public ResponseEntity<Map<String, Object>> processTask(
            @RequestBody @Validated final WorkflowProcessRequest workflowProcessingRequestParameter) throws Exception {
        return ResponseEntity.ok()
                .body(workflowProcessingService.processTask(workflowProcessingRequestParameter));
    }
}
