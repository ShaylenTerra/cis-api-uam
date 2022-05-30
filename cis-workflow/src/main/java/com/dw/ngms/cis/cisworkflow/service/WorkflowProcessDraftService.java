package com.dw.ngms.cis.cisworkflow.service;

import com.dw.ngms.cis.cisworkflow.persistence.domain.MProvinces;
import com.dw.ngms.cis.cisworkflow.persistence.domain.WorkflowProcess;
import com.dw.ngms.cis.cisworkflow.persistence.domain.WorkflowProcessDraft;
import com.dw.ngms.cis.cisworkflow.persistence.repository.ProvinceRepository;
import com.dw.ngms.cis.cisworkflow.persistence.repository.WorkflowProcessDraftRepository;
import com.dw.ngms.cis.cisworkflow.persistence.repository.WorkflowProcessRepository;
import com.dw.ngms.cis.cisworkflow.request.WorkflowProcessDraftRequest;
import com.dw.ngms.cis.cisworkflow.utility.GenericConverter;
import com.dw.ngms.cis.cisworkflow.utility.WorkflowUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author : prateekgoel
 * @since : 20/05/21, Thu
 **/
@Service
public class WorkflowProcessDraftService {

    private final WorkflowProcessDraftRepository workflowProcessDraftRepository;

    private final ProvinceRepository provinceRepository;

    private final WorkflowProcessRepository workflowProcessRepository;

    @Autowired
    public WorkflowProcessDraftService(WorkflowProcessDraftRepository workflowProcessDraftRepository,
                                       ProvinceRepository provinceRepository,
                                       WorkflowProcessRepository workflowProcessRepository) {
        this.workflowProcessDraftRepository = workflowProcessDraftRepository;
        this.provinceRepository = provinceRepository;
        this.workflowProcessRepository = workflowProcessRepository;
    }

    public ResponseEntity<?> performOperation(WorkflowProcessDraftRequest workflowProcessDraftRequest) {
        WorkflowProcessDraft workflowProcessDraft = GenericConverter.workflowProcessDraftRequestToDAO(workflowProcessDraftRequest);
        workflowProcessDraft = WorkflowUtility.addDefaultItemToWorkflowProcessDraftDAO(workflowProcessDraft);
        workflowProcessDraft.setProcessId(workflowProcessDraftRepository.getProcessId());
        workflowProcessDraft = workflowProcessDraftRepository.saveAndFlush(workflowProcessDraft);
        return ResponseEntity.status(HttpStatus.OK).body(workflowProcessDraft.getProcessId());
    }

    public ResponseEntity<?> performUpdateOperation(WorkflowProcessDraftRequest workflowProcessDraftRequest) {
        WorkflowProcessDraft workflowprocessDraft = workflowProcessDraftRepository.findUsingProcessId(workflowProcessDraftRequest.getProcessId());
        workflowprocessDraft = WorkflowUtility.setUpdateItemToWorkflowDraft(workflowProcessDraftRequest, workflowprocessDraft);
        workflowProcessDraftRepository.updateWorkflowProcessDraft(workflowprocessDraft.getProcessId(),
                workflowprocessDraft.getName(),
                workflowprocessDraft.getDescription(),
                workflowprocessDraft.getConfiguration(),
                workflowprocessDraft.getDesignData(),
                workflowprocessDraft.getDraftVersion());

        return ResponseEntity.status(HttpStatus.OK).body(workflowprocessDraft.getProcessId());
    }

    public ResponseEntity<?> performPublishOperation(Long processId, Map<String, Object> workflowProcessItem) {
        int status = performPulishAdd(processId, workflowProcessItem);
        return ResponseEntity.status(HttpStatus.OK).body(status);
    }

    public int performPublishUpdate(Long processId) {
        WorkflowProcessDraft workflowprocessDraft = workflowProcessDraftRepository.getOne(processId);
        return 0;
    }

    public void updateWorkflowPublishProcessItem(WorkflowProcessDraft workflowprocessDraft, Map<String, Object> workflowProcessItemMap) {
        if (workflowProcessItemMap.containsKey("name"))
            workflowprocessDraft.setName((String) workflowProcessItemMap.get("name"));
        if (workflowProcessItemMap.containsKey("description"))
            workflowprocessDraft.setDescription((String) workflowProcessItemMap.get("description"));
        if (workflowProcessItemMap.containsKey("configuration"))
            workflowprocessDraft.setConfiguration((String) workflowProcessItemMap.get("configuration"));
        if (workflowProcessItemMap.containsKey("designData"))
            workflowprocessDraft.setDesignData((String) workflowProcessItemMap.get("designData"));
        workflowprocessDraft.setDraftVersion(workflowprocessDraft.getDraftVersion().add(new BigDecimal("0.1")));
    }

    public int performPulishAdd(Long processId, Map<String, Object> workflowProcessItem) {
        Optional<WorkflowProcessDraft> workflowProcessDraftOptional = workflowProcessDraftRepository.findById(processId);
        WorkflowProcessDraft workflowprocessDraft = workflowProcessDraftOptional.get();
        final Long provinceId = workflowprocessDraft.getProvinceId();
        updateWorkflowPublishProcessItem(workflowprocessDraft, workflowProcessItem);
        workflowProcessDraftRepository.updateWorkflowProcessDraft(workflowprocessDraft.getProcessId(), workflowprocessDraft
                .getName(), workflowprocessDraft .getDescription(),
                workflowprocessDraft.getConfiguration(),
                workflowprocessDraft.getDesignData(),
                workflowprocessDraft.getDraftVersion());
        if (provinceId == -1L) {
            workflowProcessDraftRepository.deletePublishedItem(processId);
            this.workflowProcessRepository.deletePublishedItem(processId);
            final WorkflowProcessDraft newDraft = WorkflowUtility.createNewDraft(workflowprocessDraft, provinceId);
            WorkflowProcess mainWorkflowProcessItem = WorkflowUtility.createNewPublishDraft(newDraft, provinceId);
            mainWorkflowProcessItem.setProcessId(workflowprocessDraft.getProcessId());
            this.workflowProcessRepository.saveAndFlush(mainWorkflowProcessItem);
            List<MProvinces> provincesList = this.provinceRepository.findAll();
            long newProcessIdStart = workflowProcessDraftRepository.getProcessId();
            for (MProvinces province : provincesList) {
                if (province.getProvinceId() == -1L)
                    continue;
                WorkflowProcessDraft workflowPublishDraft = WorkflowUtility.createNewDraft(workflowprocessDraft, province.getProvinceId());
                workflowPublishDraft.setProcessId(newProcessIdStart);
                newProcessIdStart++;
                workflowProcessDraftRepository.saveAndFlush(workflowPublishDraft);
                WorkflowProcess workflowprocess = WorkflowUtility.createNewPublishDraft(workflowPublishDraft, province.getProvinceId());
                this.workflowProcessRepository.saveAndFlush(workflowprocess);
            }
        } else {
            workflowprocessDraft.setPublishedVersion(workflowprocessDraft.getPublishedVersion().add(new BigDecimal("1.0")));
            workflowProcessDraftRepository.updatePublishedVersionDraft(workflowprocessDraft.getProcessId(), workflowprocessDraft.getPublishedVersion());
            WorkflowProcess workflowprocess = WorkflowUtility.copyPublishFromDraft(workflowprocessDraft);
            this.workflowProcessRepository.updateWorkflowProcess(workflowprocess.getProcessId(),
                    workflowprocess.getConfiguration(),
                    workflowprocess.getDesignData(),
                    workflowprocess.getItemIdModule(),
                    workflowprocess.getDraftVersion(),
                    workflowprocess.getPublishedVersion());
        }
        return 0;
    }

    public ResponseEntity<?> performGetProcessListOperation(long provinceId, int itemidModule, String from) {
        List<Object[]> workflowResult;
        if (from.equalsIgnoreCase(WorkflowUtility.WORKFLOW_PUBLISH)) {
            if (provinceId != 0L) {
                if (itemidModule != 0) {
                    workflowResult = this.workflowProcessRepository.findByItemIdModuleAndProvinceId(provinceId, itemidModule);
                } else {
                    workflowResult = this.workflowProcessRepository.findByProvinceId(provinceId);
                }
            } else if (itemidModule != 0) {
                workflowResult = this.workflowProcessRepository.findByItemIdModule(itemidModule);
            } else {
                workflowResult = this.workflowProcessRepository.findByAll();
            }
        } else if (provinceId != 0L) {
            if (itemidModule != 0) {
                workflowResult = workflowProcessDraftRepository.findByItemIdModuleAndProvinceId(provinceId, itemidModule);
            } else {
                workflowResult = workflowProcessDraftRepository.findByProvinceId(provinceId);
            }
        } else if (itemidModule != 0) {
            workflowResult = workflowProcessDraftRepository.findByItemIdModule(itemidModule);
        } else {
            workflowResult = workflowProcessDraftRepository.findByAll();
        }
        Map<Integer, String> provincesList = null;
        if (workflowResult != null)
            provincesList = WorkflowUtility.provinceListToMap(this.provinceRepository.findAll());
        return ResponseEntity.ok()
                .body(WorkflowUtility.listToMap(workflowResult, provincesList, "list", from));
    }

    public ResponseEntity<?> performGetProcessDetailsOperation(Long processId, String from) {
        List<Object[]> returnList;
        if (from.equalsIgnoreCase(WorkflowUtility.WORKFLOW_PUBLISH)) {
            returnList = this.workflowProcessRepository.findByProcessId(processId);
        } else {
            returnList = workflowProcessDraftRepository.findByProcessId(processId);
        }
        Map<Integer, String> provincesList = null;
        if (returnList != null)
            provincesList = WorkflowUtility.provinceListToMap(this.provinceRepository.findAll());
        return ResponseEntity.ok()
                .body(WorkflowUtility.listToMap(returnList, provincesList, "details", from));
    }
}
