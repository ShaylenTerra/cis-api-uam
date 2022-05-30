package com.dw.ngms.cis.service.mapper;

import com.dw.ngms.cis.persistence.domains.workflow.WorkflowUserFeedback;
import com.dw.ngms.cis.service.dto.workflow.WorkflowUserFeedbackDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * @author : pragayanshu
 * @since : 2022/03/28, Mon
 **/

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WorkflowUserFeedbackMapper {

    WorkflowUserFeedbackDto workflowUserFeedbackToWorkflowUserFeedbackDto(WorkflowUserFeedback workflowUserFeedback);

    WorkflowUserFeedback  workflowUserFeedbackDtoToWorkflowUserFeedback(WorkflowUserFeedbackDto workflowUserFeedbackDTO);

}
