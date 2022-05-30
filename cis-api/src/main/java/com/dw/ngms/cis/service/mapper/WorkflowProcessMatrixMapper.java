package com.dw.ngms.cis.service.mapper;

import com.dw.ngms.cis.persistence.domains.workflow.WorkflowProcessMatrix;
import com.dw.ngms.cis.service.dto.workflow.WorkflowProcessMatrixDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

/**
 * @author : prateekgoel
 * @since : 29/12/20, Tue
 **/
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WorkflowProcessMatrixMapper {

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "description", source = "description"),

    })
    WorkflowProcessMatrixDto workflowProcessMatrixToWorkflowProcessMatrixDto(WorkflowProcessMatrix workflowProcessMatrix);

}
