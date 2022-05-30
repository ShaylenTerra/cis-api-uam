package com.dw.ngms.cis.service.dto.workflow;

import lombok.Data;

import java.util.Date;

/**
 * @author : prateekgoel
 * @since : 30/12/20, Wed
 **/
@Data
public class WorkflowDocumentDto {

    private Long documentId;

    private String docName;

    private Date uploadedOn;

    private Long userId;

    private Long documentTypeId;

    private String notes;

    private String extension;

    private Long sizeKb;

    private Long workflowId;

    private String uploadRefId;

}
