package com.dw.ngms.cis.cisworkflow.service;

import com.dw.ngms.cis.cisworkflow.persistence.domain.*;
import com.dw.ngms.cis.cisworkflow.persistence.repository.*;
import com.dw.ngms.cis.cisworkflow.request.*;
import com.dw.ngms.cis.cisworkflow.rest.request.WorkflowProcessRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : prateekgoel
 * @since : 20/05/21, Thu
 **/
@Service
@Slf4j
@AllArgsConstructor
public class WorkflowProcessingService {


    private final TransactionsRepository transactionsRepository;


    private final WorkflowProcessRepository workflowProcessRepository;


    private final WorkflowActionRepository workflowActionRepository;


    private final WorkflowRepository workflowRepository;


    private final WorkflowProcessScreenRepository workflowprocessScreenRepository;


    private final WorkflowProcessMatrixRepository workflowprocessMatrixRepository;

    private final UserAdditionalRoleService userAdditionalRoleService;

    private final ObjectMapper objectMapper;

    private final EmailHandlerService emailHandlerService;

    private final UserRepository userRepository;

    public Map<String, Object> triggerTask(Map<String, Object> workflowProcessingRequestParameter) throws Exception {
        TriggerRequest triggerRequest = new TriggerRequest();
        triggerRequest.setRequestData(workflowProcessingRequestParameter);
        List<Object[]> returnList = this.workflowProcessRepository.findByProvinceIdAndParentProcessId(triggerRequest.getProcessid(), triggerRequest.getProvinceid());
        Map<String, Object> workflowProcessDetails = listToMap(returnList);
        triggerRequest.setChildProcessId((Long) workflowProcessDetails.get("ProcessId"));
        triggerRequest.setParentProcessId(triggerRequest.getProcessid());
        return generateAction(triggerRequest, workflowProcessDetails);

    }

    public Transactions createTransactionObject(TriggerRequest triggerRequest) {
        Transactions transactions = new Transactions();
        transactions.setStartTime(triggerRequest.getDate());
        transactions.setComments(triggerRequest.getNotes());
        transactions.setSummary(triggerRequest.getContext());
        transactions.setTType(triggerRequest.getType());
        transactions.setUserId(triggerRequest.getLoggeduserid());
        transactions.setToken("1");
        transactions.setExecutionTime(0L);
        return transactions;
    }

    public Map<String, Object> generateAction(TriggerRequest triggerRequest, Map<String, Object> workflowProcessDetails) throws Exception {
        Transactions transactions = createTransactionObject(triggerRequest);
        //this.transactionsRepository.saveAndFlush(transactions);
        Configuration config = objectMapper.readValue((String) workflowProcessDetails.get("Configuration"), Configuration.class);
        Node triggeredNode = config.getTriggerNode();
        Node nextNode = config.getNextNode(triggeredNode);
        long workflowReferenceId = workflowRepository.getWorkflowProcessCount(triggerRequest.getChildProcessId()).size();
        String referenceNumber = createReferenceNumber(config, workflowReferenceId);
        Workflow workflow = new Workflow();
        if (triggerRequest.getParentWorkflowid() != 0L)
            workflow.setParentWorkflowId(triggerRequest.getParentWorkflowid());
        workflow.setTransactionId(transactions.getTransactionId());
        workflow.setReferenceNo(referenceNumber);
        workflow.setProcessId(triggerRequest.getChildProcessId());
        workflow.setTriggeredOn(triggerRequest.getDate());
        workflow.setProvinceId(triggerRequest.getProvinceid());
        workflow.setProcessData(triggerRequest.getProcessdata());
        workflow.setInternalStatusId(Integer.parseInt(triggeredNode.getOutlinks().get(0).getStatusInternal()));
        workflow.setExternalStatusId(Integer.parseInt(triggeredNode.getOutlinks().get(0).getStatusExternal()));
        workflow.setTurnAroundDuration(config.getTurnAroundTime());
        workflow.setStatusId(1);
        workflow.setPriorityFlag(230L);
        this.workflowRepository.saveAndFlush(workflow);
        WorkflowAction workflowAction = new WorkflowAction();
        workflowAction.setActionRequired(Long.valueOf(nextNode.getActionRequired()));
        if (triggerRequest.getAssignedtouserid() == 0L) {
            // fetch userId based on provinceId and roleId
            final String role = nextNode.getRole();
            final long provinceId = triggerRequest.getProvinceid();
            final Long userIdByProvinceIdAndRoleId = userAdditionalRoleService
                    .getUserIdByProvinceIdAndRoleId(provinceId, Long.parseLong(role));
            workflowAction.setUserId(userIdByProvinceIdAndRoleId);
        } else {
            workflowAction.setUserId(triggerRequest.getAssignedtouserid());
        }
        workflowAction.setContext(triggerRequest.getContext());
        workflowAction.setWorkflowId(workflow.getWorkflowId());
        workflowAction.setNote(triggerRequest.getNotes());
        workflowAction.setTransactionId(transactions.getTransactionId());
        workflowAction.setActedOn(null);
        workflowAction.setAllocatedOn(triggerRequest.getDate());
        workflowAction.setPostedOn(triggerRequest.getDate());
        workflowAction.setTemplateId(triggeredNode.getOutlinks().get(0).getNotificationTemplateId());
        workflowAction.setEscalationTime(Long.parseLong(nextNode.getEscalation()));
        workflowAction.setNotificationTime(Long.parseLong(nextNode.getFlagReminder()));
        workflowAction.setNodeId(nextNode.getNodeID());
        workflowActionRepository.saveAndFlush(workflowAction);

        final String processName = config.getName(); //processName
        final String actionRequiredCaption = nextNode.getHeading(); // action Name
        final String referenceNo = workflow.getReferenceNo(); // referenceNumber
        final User byUserId = userRepository.findByUserId(workflowAction.getUserId());
        // send email api
        //sendEmailNotification(byUserId, referenceNo, actionRequiredCaption, processName);
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("WorkflowID", workflow.getWorkflowId());
        returnMap.put("ReferenceNumber", workflow.getReferenceNo());
        returnMap.put("TransactionId", transactions.getTransactionId());
        returnMap.put("TemplateID", triggeredNode.getOutlinks().get(0).getNotificationTemplateId());
        return returnMap;
    }

    public String createReferenceNumber(Configuration config, long workflowReferenceId) {
        workflowReferenceId++;
        StringBuilder workflowReferenceIdString = new StringBuilder(String.valueOf(workflowReferenceId));
        int len = workflowReferenceIdString.length();
        if (len < 4) {
            int i = 0;
            while (i < 4 - len) {
                workflowReferenceIdString.insert(0, "0");
                i++;
            }
        }
        return config.getPrefix() + workflowReferenceIdString + config.getPostfix();
    }

    public Map<String, Object> listToMap(List<Object[]> list) throws Exception {
        if (list.size() > 1)
            throw new Exception("More than 1 row discovered for process id and province id");
        Map<String, Object> tempMap = new HashMap<>();
        for (Object[] workflowItemList : list) {
            tempMap.put("Name", workflowItemList[0]);
            tempMap.put("Description", workflowItemList[1]);
            tempMap.put("ProvinceId", workflowItemList[2]);
            tempMap.put("Published Version", workflowItemList[3]);
            tempMap.put("Dated", workflowItemList[4]);
            tempMap.put("Draft_Version", workflowItemList[5]);
            tempMap.put("ProcessId", workflowItemList[6]);
            tempMap.put("ItemID_Moduel", workflowItemList[7]);
            tempMap.put("Configuration", workflowItemList[8]);
            tempMap.put("DesignData", workflowItemList[9]);
        }
        return tempMap;
    }

    public String getProcessNodeDetails(final Long processId, final Long nodeId) throws Exception {

        List<Object[]> returnList = this.workflowProcessRepository.findByProcessId(processId);
        Map<String, Object> workflowProcessDetails = listToMap(returnList);
        Configuration config = objectMapper.readValue((String) workflowProcessDetails.get("Configuration"), Configuration.class);
        Node node = config.getNode(nodeId);
        node.setFormName(getFormName(node.getFormID()));
        node.setActionRequiredCaption(getActionDescription(node.getActionRequired()));
        List<Outlink> outlinkList = node.getOutlinks();
        for (Outlink outlink : outlinkList) {
            final Long nextNodeID = outlink.getNextNodeID();
            final Node node1 = config.getNode(nextNodeID);
            outlink.setNextNodeRoleId(node1.getRole());
            outlink.setActionCaption(getActionDescription(outlink.getAction()));
        }

        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);


    }

    public String getFormName(String formid) {
        if (formid == null || formid.equals(""))
            return "";
        try {
            Long formidInt = Long.valueOf(formid);
            WorkflowProcessScreen formIdList = this.workflowprocessScreenRepository.findByScreenId(formidInt);
            if (null == formIdList)
                return "No formID found";

            return formIdList.getName();
        } catch (NumberFormatException ex) {
            return "";
        }
    }

    public String getActionDescription(String actionID) {
        if (actionID == null || actionID.equals(""))
            return "";
        try {
            Long actionIdInt = Long.valueOf(actionID);
            final WorkflowProcessMatrix actionIdList = workflowprocessMatrixRepository.findByMatrixId(actionIdInt);
            if (null == actionIdList)
                return "No action found";
            return actionIdList.getDescription();
        } catch (NumberFormatException ex) {
            return "";
        }
    }

    public Map<String, Object> processTask(final WorkflowProcessRequest workflowProcessingRequestParameter) throws Exception {
        ProcessTaskRequest processTaskRequest = new ProcessTaskRequest();
        processTaskRequest.setRequestData(workflowProcessingRequestParameter);
        WorkflowAction workflowAction = workflowActionRepository.findByActionId(processTaskRequest.getActionId());
        processTaskRequest.setWorkflowId(workflowAction.getWorkflowId());
        Workflow workflow = workflowRepository.findByWorkflowId(processTaskRequest.getWorkflowId());
        Transactions transactions = new Transactions();
        transactions.setStartTime(processTaskRequest.getDate());
        transactions.setComments(processTaskRequest.getNotes());
        transactions.setSummary(processTaskRequest.getContext());
        transactions.setTType(processTaskRequest.getType());
        transactions.setUserId(processTaskRequest.getLoggedUserId());
        transactions.setToken("1");
        transactions.setExecutionTime(0L);
        transactionsRepository.save(transactions);
        workflowAction.setActedOn(processTaskRequest.getDate());
        workflowAction.setActionTaken(processTaskRequest.getActionTakenId());
        workflowAction.setActionTransactionId(transactions.getTransactionId());
        this.workflowActionRepository.save(workflowAction);
        List<Object[]> returnList = workflowProcessRepository.findByProcessId(processTaskRequest.getProcessId());
        Map<String, Object> workflowProcessDetails = listToMap(returnList);
        Configuration config = objectMapper.readValue((String) workflowProcessDetails.get("Configuration"), Configuration.class);
        Node node = config.getNode(processTaskRequest.getCurrentNodeId());
        Node nextActionNode = config.getNextNodeForAction(node, processTaskRequest.getActionTakenId());
        Outlink currentNodeOutlinkForactionNode = node.getNextNodeOutlink(nextActionNode.getNodeID());
        workflow.setExternalStatusId(Integer.parseInt(currentNodeOutlinkForactionNode.getStatusExternal()));
        workflow.setInternalStatusId(Integer.parseInt(currentNodeOutlinkForactionNode.getStatusInternal()));
        workflow.setCompletedOn(LocalDateTime.now());
        boolean isUpdateDatabase = nextActionNode.getNodeType().equals("UpdateDatabase");
        if (isUpdateDatabase) {
            workflow.setStatusId(2);
        } else {
            workflow.setStatusId(1);
        }
        this.workflowRepository.saveAndFlush(workflow);
        if (isUpdateDatabase) {
            Map<String, Object> map = new HashMap<>();
            map.put("workflowId", workflow.getWorkflowId());
            map.put("referenceNumber", workflow.getReferenceNo());
            map.put("transactionId", transactions.getTransactionId());
            map.put("templateId", currentNodeOutlinkForactionNode.getNotificationTemplateId());
            return map;
        }
        WorkflowAction newWorkflowAction = new WorkflowAction();
        newWorkflowAction.setActionRequired(Long.valueOf(nextActionNode.getActionRequired()));
        newWorkflowAction.setContext(processTaskRequest.getContext());
        newWorkflowAction.setWorkflowId(workflow.getWorkflowId());
        newWorkflowAction.setNote(processTaskRequest.getNotes());
        newWorkflowAction.setTransactionId(transactions.getTransactionId());
        newWorkflowAction.setActedOn(null);
        if (processTaskRequest.getAssignedtouserid() == 0L) {
            // get userId
            // get provinceId
            // get first user for this provinceId and roleId and assigned it to process.
            final String role = nextActionNode.getRole();
            final long provinceId = workflow.getProvinceId();
            final Long userIdByProvinceIdAndRoleId = userAdditionalRoleService
                    .getUserIdByProvinceIdAndRoleId(provinceId, Long.parseLong(role));
            newWorkflowAction.setUserId(userIdByProvinceIdAndRoleId);
        } else {
            newWorkflowAction.setUserId(processTaskRequest.getAssignedtouserid());
        }
        newWorkflowAction.setAllocatedOn(processTaskRequest.getDate());
        newWorkflowAction.setPostedOn(processTaskRequest.getDate());
        newWorkflowAction.setTemplateId(currentNodeOutlinkForactionNode.getNotificationTemplateId());
        newWorkflowAction.setEscalationTime(Long.parseLong(nextActionNode.getEscalation()));
        newWorkflowAction.setNotificationTime(Long.parseLong(nextActionNode.getFlagReminder()));
        newWorkflowAction.setNodeId(nextActionNode.getNodeID());
        newWorkflowAction.setLinkId(node.getNodeID());
        workflowActionRepository.save(newWorkflowAction);

       // final String processName = config.getName(); //processName
        //final String actionRequiredCaption = nextActionNode.getHeading(); // action Name
        //final String referenceNo = workflow.getReferenceNo(); // referenceNumber
        //final User byUserId = userRepository.findByUserId(workflowAction.getUserId());
        // send email api
        //sendEmailNotification(byUserId, referenceNo, actionRequiredCaption, processName);

        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("workflowId", newWorkflowAction.getWorkflowId());
        returnMap.put("referenceNumber", workflow.getReferenceNo());
        returnMap.put("transactionId", transactions.getTransactionId());
        returnMap.put("templateId", currentNodeOutlinkForactionNode.getNotificationTemplateId());
        return returnMap;

    }

    //@Async
    //public void sendEmailNotification(final User byUserId,
                                     // final String referenceNo,
                                    //  final String actionRequiredCaption,
                                 //     final String processName) {
      //  emailHandlerService.triggerTaskEmail(byUserId, referenceNo, actionRequiredCaption, processName);
    //}

}
