package com.dw.ngms.cis.cisworkflow.request;

import com.dw.ngms.cis.cisworkflow.persistence.repository.WorkflowProcessRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author : prateekgoel
 * @since : 20/05/21, Thu
 **/
@Data
public class TriggerRequest {

    @Autowired
    private WorkflowProcessRepository workflowProcessRepository;

    private long processid;

    private long provinceid;

    private long loggeduserid;

    private String notes;

    private String context;

    private int type;

    private long childProcessId;

    private long parentProcessId;

    private long assignedtouserid;

    private long parentWorkflowid;

    private LocalDateTime date;

    private String processdata;

    public void setRequestData(Map<String, Object> triggerRequestParameter) {
        setProcessid(((Integer) triggerRequestParameter.get("processid")).longValue());
        setProvinceid(((Integer) triggerRequestParameter.get("provinceid")).longValue());
        setLoggeduserid(((Integer) triggerRequestParameter.get("loggeduserid")).longValue());
        setNotes((String) triggerRequestParameter.get("notes"));
        setContext((String) triggerRequestParameter.get("context"));
        setType(((Integer) triggerRequestParameter.get("type")).intValue());
        setProcessdata((String) triggerRequestParameter.get("processdata"));
        setAssignedtouserid(((Integer) triggerRequestParameter.get("assignedtouserid")).longValue());
        setParentWorkflowid(((Integer) triggerRequestParameter.get("parentworkflowid")).longValue());
        setDate(LocalDateTime.now());
    }
}
