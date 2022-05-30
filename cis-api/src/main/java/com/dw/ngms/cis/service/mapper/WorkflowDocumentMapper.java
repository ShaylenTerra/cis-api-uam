package com.dw.ngms.cis.service.mapper;

import com.dw.ngms.cis.persistence.domains.workflow.WorkflowDocuments;
import com.dw.ngms.cis.service.dto.workflow.PaymentDto;
import com.dw.ngms.cis.service.dto.workflow.WorkflowDocumentDto;
import com.dw.ngms.cis.web.vm.workflow.UploadWorkflowDocsVm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.ReportingPolicy;

/**
 * @author : prateekgoel
 * @since : 30/12/20, Wed
 **/
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface WorkflowDocumentMapper {

    WorkflowDocumentDto workflowDocumentsToWorkflowDocumentsDto(WorkflowDocuments workflowDocuments);

    @Mappings({
            @Mapping(target = "workflowId", source = "workflowId"),
            @Mapping(target = "userId", source = "userId"),
            @Mapping(target = "documentTypeId", source = "documentType"),
            @Mapping(target = "notes", source = "comment")
    })
    WorkflowDocuments uploadWorkflowDocsVmToWorkflowDocuments(UploadWorkflowDocsVm uploadWorkflowDocsVm);

    @Mappings({
            @Mapping(target = "workflowId", source = "workflowId"),
            @Mapping(target = "notes", source = "comments"),
            @Mapping(target = "documentTypeId", source = "documentTypeId"),
            @Mapping(target = "userId", source = "userId")

    })
    WorkflowDocuments proofOfPaymentDtoToWorkflowDocuments(PaymentDto paymentDto);

}
