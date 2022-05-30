package com.dw.ngms.cis.cisworkflow.utility;


import com.dw.ngms.cis.cisworkflow.persistence.domain.*;
import com.dw.ngms.cis.cisworkflow.request.WorkflowProcessDraftRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : prateekgoel
 * @since : 20/05/21, Thu
 **/
public class WorkflowUtility {

    public static String WORKFLOW_DRAFT = "DRAFT";

    public static String WORKFLOW_PUBLISH = "PUBLISHED";

    public static String RECORD_CREATE_DRAFT = "Record Generated";

    public static String RECORD_UPDATE_DRAFT = "Update Draft";

    public static boolean DEFAULT_IS_ACTIVE = true;

    public static BigDecimal DEFAULT_DRAFT_VERSION = new BigDecimal("0.1");

    public static BigDecimal DEFAULT_PUBLISH_VERSION = new BigDecimal("0.0");

    public static WorkflowProcessDraft addDefaultItemToWorkflowProcessDraftDAO(WorkflowProcessDraft workflowProcessDraft) {
        workflowProcessDraft.setIsActive(1);
        workflowProcessDraft.setDraftVersion(DEFAULT_DRAFT_VERSION);
        workflowProcessDraft.setDated(LocalDateTime.now());
        workflowProcessDraft.setPublishedVersion(DEFAULT_PUBLISH_VERSION);
        return workflowProcessDraft;
    }

    public static WorkflowProcessDraft setUpdateItemToWorkflowDraft(WorkflowProcessDraftRequest workflowProcessDraftRequest, WorkflowProcessDraft workflowprocessDraft) {
        workflowprocessDraft.setDraftVersion(workflowprocessDraft.getDraftVersion().add(new BigDecimal("0.1")));
        workflowprocessDraft.setName(workflowProcessDraftRequest.getName());
        workflowprocessDraft.setDescription(workflowProcessDraftRequest.getDescription());
        workflowprocessDraft.setConfiguration(workflowProcessDraftRequest.getConfiguration());
        workflowprocessDraft.setDesignData(workflowProcessDraftRequest.getDesignData());
        return workflowprocessDraft;
    }

    public static WorkflowProcessDraft createNewDraft(WorkflowProcessDraft workflowprocessDraft, Long provinceId) {
        WorkflowProcessDraft newWorkflowProcessDraft = new WorkflowProcessDraft();
        newWorkflowProcessDraft.setDesignData(workflowprocessDraft.getDesignData());
        newWorkflowProcessDraft.setName(workflowprocessDraft.getName());
        newWorkflowProcessDraft.setConfiguration(workflowprocessDraft.getConfiguration());
        newWorkflowProcessDraft.setUserId(workflowprocessDraft.getUserId());
        newWorkflowProcessDraft.setItemIdModule(workflowprocessDraft.getItemIdModule());
        newWorkflowProcessDraft.setDescription(workflowprocessDraft.getDescription());
        newWorkflowProcessDraft.setIsActive(workflowprocessDraft.getIsActive());
        newWorkflowProcessDraft.setProvinceId(provinceId);
        newWorkflowProcessDraft.setDated(LocalDateTime.now());
        newWorkflowProcessDraft.setParentProcessId(workflowprocessDraft.getProcessId());
        newWorkflowProcessDraft.setDraftVersion(workflowprocessDraft.getDraftVersion().add(new BigDecimal("0.1")));
        newWorkflowProcessDraft.setPublishedVersion(workflowprocessDraft.getPublishedVersion().add(new BigDecimal("1.0")));
        return newWorkflowProcessDraft;
    }

    public static WorkflowProcess createNewPublishDraft(WorkflowProcessDraft workflowprocessDraft, long provinceId) {
        WorkflowProcess newWorkflowProcess = new WorkflowProcess();
        newWorkflowProcess.setProcessId(workflowprocessDraft.getProcessId());
        newWorkflowProcess.setDesignData(workflowprocessDraft.getDesignData());
        newWorkflowProcess.setName(workflowprocessDraft.getName());
        newWorkflowProcess.setConfiguration(workflowprocessDraft.getConfiguration());
        newWorkflowProcess.setItemIdModule(workflowprocessDraft.getItemIdModule());
        newWorkflowProcess.setUserId(workflowprocessDraft.getUserId());
        newWorkflowProcess.setDescription(workflowprocessDraft.getDescription());
        newWorkflowProcess.setIsActive(workflowprocessDraft.getIsActive());
        newWorkflowProcess.setProvinceId(provinceId);
        newWorkflowProcess.setDated(LocalDateTime.now());
        newWorkflowProcess.setParentProcessId(workflowprocessDraft.getParentProcessId());
        newWorkflowProcess.setDraftVersion(workflowprocessDraft.getDraftVersion());
        newWorkflowProcess.setPublishedVersion(workflowprocessDraft.getPublishedVersion());
        return newWorkflowProcess;
    }

    public static WorkflowProcess copyPublishFromDraft(WorkflowProcessDraft workflowprocessDraft) {
        WorkflowProcess newWorkflowProcess = new WorkflowProcess();
        newWorkflowProcess.setProcessId(workflowprocessDraft.getProcessId());
        newWorkflowProcess.setDesignData(workflowprocessDraft.getDesignData());
        newWorkflowProcess.setName(workflowprocessDraft.getName());
        newWorkflowProcess.setConfiguration(workflowprocessDraft.getConfiguration());
        newWorkflowProcess.setItemIdModule(workflowprocessDraft.getItemIdModule());
        newWorkflowProcess.setUserId(workflowprocessDraft.getUserId());
        newWorkflowProcess.setDescription(workflowprocessDraft.getDescription());
        newWorkflowProcess.setIsActive(workflowprocessDraft.getIsActive());
        newWorkflowProcess.setProvinceId(workflowprocessDraft.getProvinceId());
        newWorkflowProcess.setDated(LocalDateTime.now());
        newWorkflowProcess.setParentProcessId(workflowprocessDraft.getParentProcessId());
        newWorkflowProcess.setDraftVersion(workflowprocessDraft.getDraftVersion());
        newWorkflowProcess.setPublishedVersion(workflowprocessDraft.getPublishedVersion());
        return newWorkflowProcess;
    }

    public static List<Map<String, Object>> listToMap(List<Object[]> list, Map<Integer, String> provinces, String resultType, String from) {
        List<Map<String, Object>> returnList = new ArrayList<>();
        for (Object[] workflowItemList : list) {
            Map<String, Object> tempMap = new HashMap<>();
            tempMap.put("Name", workflowItemList[0]);
            tempMap.put("Description", workflowItemList[1]);
            tempMap.put("ProvinceId", workflowItemList[2]);
            tempMap.put("Province Name", provinces.get(((Long) workflowItemList[2]).intValue()));
            if (from.equalsIgnoreCase(WORKFLOW_PUBLISH)) {
                tempMap.put("Published Version", workflowItemList[3]);
            } else {
                tempMap.put("Published Version", 0);
            }
            tempMap.put("Dated", workflowItemList[4]);
            tempMap.put("Draft_Version", workflowItemList[5]);
            tempMap.put("ProcessId", workflowItemList[6]);
            if (resultType.equals("details")) {
                tempMap.put("ItemID_Moduel", workflowItemList[7]);
                tempMap.put("Configuration", workflowItemList[8]);
                tempMap.put("DesignData", workflowItemList[9]);
            }
            returnList.add(tempMap);
        }
        return returnList;
    }

    public static Map<Integer, String> provinceListToMap(List<MProvinces> provinces) {
        Map<Integer, String> provinceMap = new HashMap<>();
        for (MProvinces prov : provinces)
            provinceMap.put(prov.getProvinceId().intValue(), prov.getProvinceName());
        return provinceMap;
    }

    public static List<Map<String, Object>> matrixListToMatrixMap(List<WorkflowProcessMatrix> list) {
        List<Map<String, Object>> returnList = new ArrayList<>();
        for (WorkflowProcessMatrix workflowprocessMatrixItem : list) {
            Map<String, Object> tempMap = new HashMap<>();
            tempMap.put("Id", workflowprocessMatrixItem.getMatrixId());
            tempMap.put("Description", workflowprocessMatrixItem.getDescription());
            tempMap.put("Tag", workflowprocessMatrixItem.getTag());
            tempMap.put("TypeFlag", workflowprocessMatrixItem.getTypeflag());
            returnList.add(tempMap);
        }
        return returnList;
    }

    public static List<Map<String, Object>> screenListToScreenMap(List<WorkflowProcessScreen> list) {
        List<Map<String, Object>> returnList = new ArrayList<>();
        for (WorkflowProcessScreen workflowprocessScreen : list) {
            Map<String, Object> tempMap = new HashMap<>();
            tempMap.put("ScreenId", workflowprocessScreen.getScreenId());
            tempMap.put("Name", workflowprocessScreen.getName());
            tempMap.put("ModuleID", workflowprocessScreen.getItemIdModule());
            tempMap.put("Description", workflowprocessScreen.getDescription());
            returnList.add(tempMap);
        }
        return returnList;
    }

    public static List<Map<String, Object>> listItemListTolistItemMap(List<MListItem> list) {
        List<Map<String, Object>> returnList = new ArrayList<>();
        for (MListItem listitem : list) {
            Map<String, Object> tempMap = new HashMap<>();
            tempMap.put("ItemId", listitem.getItemId());
            tempMap.put("ListCode", listitem.getListCode());
            tempMap.put("Caption", listitem.getCaption());
            returnList.add(tempMap);
        }
        return returnList;
    }

    public static List<Map<String, Object>> listTimingResultToMap(List<MOfficeTimings> list) {
        List<Map<String, Object>> returnList = new ArrayList<>();
        for (MOfficeTimings listitem : list) {
            Map<String, Object> tempMap = new HashMap<>();
            tempMap.put("TimingId", listitem.getTimingId());
            tempMap.put("Description", listitem.getDescription());
            tempMap.put("FromDate", listitem.getFromDate());
            tempMap.put("ToDate", listitem.getToDate());
            tempMap.put("FromTime", listitem.getFromTime());
            tempMap.put("ToTime", listitem.getToTime());
            tempMap.put("Status", listitem.getStatus());
            tempMap.put("ProvinceName", listitem.getProvinceId());
            returnList.add(tempMap);
        }
        return returnList;
    }

    public static List<Map<String, Object>> roleListToRoleMap(List<Roles> roleList) {
        List<Map<String, Object>> returnList = new ArrayList<>();
        for (Roles role : roleList) {
            Map<String, Object> tempMap = new HashMap<>();
            tempMap.put("RoleID", role.getRoleId());
            tempMap.put("RoleName", role.getRoleName());
            tempMap.put("RoleCode", role.getRoleCode());
            returnList.add(tempMap);
        }
        return returnList;
    }

    public static List<Map<String, Object>> templateListToTemplateMap(List<MTemplates> list) {
        List<Map<String, Object>> returnList = new ArrayList<>();
        for (MTemplates template : list) {
            Map<String, Object> tempMap = new HashMap<>();
            tempMap.put("TemplateID", template.getTemplateId());
            tempMap.put("ItemID_Module", template.getItemIdModule());
            tempMap.put("TemplateName", template.getTemplateName());
            returnList.add(tempMap);
        }
        return returnList;
    }

    public static long getNextProcessId(List<Object[]> listOfProcessId) {
        long max = 0L;
        for (Object[] obj : listOfProcessId) {
            if (max < (Long) obj[0])
                max = (Long) obj[0];
        }
        return max + 1L;
    }

    public static Long getNextTimingId(List<Object[]> listOfProcessId) {
        long max = 0L;
        for (Object[] obj : listOfProcessId) {
            if (max < (Long) obj[0])
                max = (Long) obj[0];
        }
        return max + 1L;
    }

}
