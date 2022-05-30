package com.dw.ngms.cis.utilities;

import com.dw.ngms.cis.persistence.domains.workflow.Workflow;
import com.dw.ngms.cis.persistence.domains.workflow.WorkflowAction;
import com.dw.ngms.cis.persistence.domains.workflow.WorkflowProcessData;
import com.dw.ngms.cis.persistence.repository.workflow.WorkflowActionRepository;
import com.dw.ngms.cis.persistence.repository.workflow.WorkflowRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Objects;

/**
 * @author : prateekgoel
 * @since : 20/06/21, Sun
 **/
@Component
@Slf4j
public class WorkflowUtils {

    private final WorkflowRepository workflowRepository;

    private final WorkflowActionRepository workflowActionRepository;

    private final ObjectMapper objectMapper;

    @Autowired
    public WorkflowUtils(WorkflowRepository workflowRepository,
                         WorkflowActionRepository workflowActionRepository,
                         ObjectMapper objectMapper) {
        this.workflowRepository = workflowRepository;
        this.workflowActionRepository = workflowActionRepository;
        this.objectMapper = objectMapper;
    }

    public WorkflowProcessData getProcessData(final Long workflowId) {
        final Workflow byWorkflowId = workflowRepository.findByWorkflowId(workflowId);
        final String processData = byWorkflowId.getProcessData();
        try {
            return objectMapper
                    .readValue(processData, WorkflowProcessData.class);

        } catch (Exception e) {
            log.error("error  occurs while parsing workflow processing data with cause [{}]", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public String getReferenceNumber(final Long workflowId) {
        if (null == workflowId)
            return null;
        final Workflow byWorkflowId = workflowRepository.findByWorkflowId(workflowId);
        if (null != byWorkflowId) {
            return byWorkflowId.getReferenceNo();
        } else {
            return null;
        }
    }

    public String getDecisionNotesForWorkflowId(final Long workflowId){
       if(null == workflowId || workflowId <= 0)
           return null;
        return workflowActionRepository.findByWorkflowId(workflowId).stream()
                .map(WorkflowAction::getNote)
        .findFirst().get();
    }
}
