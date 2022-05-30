package com.dw.ngms.cis.cisworkflow.rest.web;

import com.dw.ngms.cis.cisworkflow.request.WorkflowProcessDraftRequest;
import com.dw.ngms.cis.cisworkflow.service.*;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author : prateekgoel
 * @since : 20/05/21, Thu
 **/
@RestController
@AllArgsConstructor
public class AddNewWorkflowDraftController {

    private final WorkflowProcessDraftService workflowProcessDraftService;

    private final WorkflowMatrixService workflowMatrixService;

    private final WorkflowScreenService workflowScreenService;

    private final ListItemService listItemService;

    private final ProvinceService provinceService;

    private final RoleService roleService;

    private final TemplateService templateService;


    @PostMapping({"/addworkflowDraft"})
    public ResponseEntity<?> addWorkflowProcessDraft(@RequestBody WorkflowProcessDraftRequest workflowProcessDraftRequest) {
        return this.workflowProcessDraftService.performOperation(workflowProcessDraftRequest);
    }


    @PostMapping({"/updateworkflowDraft"})
    public ResponseEntity<?> updateWorkflowProcessDraft(@RequestBody WorkflowProcessDraftRequest workflowProcessDraftRequest) {
        return this.workflowProcessDraftService.performUpdateOperation(workflowProcessDraftRequest);
    }


    @PostMapping({"/publishDraft"})
    public ResponseEntity<?> publishWorkflowDraft(@RequestBody Map<String, Object> workflowPublishItem, @RequestParam Long processID) {
        return this.workflowProcessDraftService.performPublishOperation(processID, workflowPublishItem);
    }


    @PostMapping({"/addWorkflowMatrixItem"})
    public ResponseEntity<?> addWorkflowMatrixItem(@RequestBody Map<String, Object> workflowMatrixItem) {
        return this.workflowMatrixService.addItem(workflowMatrixItem);
    }


    @GetMapping({"/getProcessList"})
    public ResponseEntity<?> getProcessList(@RequestParam long provinceId, @RequestParam int itemidModuel, @RequestParam String from) {
        return this.workflowProcessDraftService.performGetProcessListOperation(provinceId, itemidModuel, from);
    }


    @GetMapping({"/getProcessDetails"})
    public ResponseEntity<?> getProcessList(@RequestParam Long processId, @RequestParam String from) {
        return this.workflowProcessDraftService.performGetProcessDetailsOperation(processId, from);
    }


    @GetMapping({"/getWorkflowProcessMatrix"})
    public ResponseEntity<?> getWorkflowProcessMatrix() {
        return this.workflowMatrixService.performGetWorkflowProcessMatrixOperation();
    }


    @GetMapping({"/getWorkflowProcessScreen"})
    public ResponseEntity<?> getWorkflowProcessScreen() {
        return this.workflowScreenService.performGetWorkflowProcessScreenOperation();
    }


    @GetMapping({"/getListItems"})
    public ResponseEntity<?> getListItems(@RequestParam Long listCode) {
        return this.listItemService.performGetListItem(listCode);
    }


    @GetMapping({"/getProvinces"})
    public ResponseEntity<?> getProvinces() {
        return this.provinceService.getProvinceList();
    }


    @GetMapping({"/getRoles"})
    public ResponseEntity<?> getRoles() {
        return this.roleService.getRoleList();
    }


    @GetMapping({"/getTemplates"})
    public ResponseEntity<?> getTemplates() {
        return this.templateService.getTemplates();
    }
}
