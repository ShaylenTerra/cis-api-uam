package com.dw.ngms.cis.cisworkflow.utility;

import com.dw.ngms.cis.cisworkflow.persistence.domain.WorkflowProcessDraft;
import com.dw.ngms.cis.cisworkflow.request.WorkflowProcessDraftRequest;

/**
 * @author : prateekgoel
 * @since : 20/05/21, Thu
 **/
public class GenericConverter {

    public static WorkflowProcessDraft workflowProcessDraftRequestToDAO(WorkflowProcessDraftRequest workflowProcessDraftRequest) {
        WorkflowProcessDraft workflowProcessDraft = new WorkflowProcessDraft();
        workflowProcessDraft.setName(workflowProcessDraftRequest.getName());
        workflowProcessDraft.setDescription(workflowProcessDraftRequest.getDescription());
        workflowProcessDraft.setItemIdModule(workflowProcessDraftRequest.getItemIdModule());
        workflowProcessDraft.setProvinceId(workflowProcessDraftRequest.getProvinceId());
        workflowProcessDraft.setConfiguration(workflowProcessDraftRequest.getConfiguration());
        workflowProcessDraft.setUserId(workflowProcessDraftRequest.getUserId());
        return workflowProcessDraft;
    }

}
