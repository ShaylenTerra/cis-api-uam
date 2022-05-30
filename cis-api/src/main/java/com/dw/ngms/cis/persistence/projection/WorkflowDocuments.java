package com.dw.ngms.cis.persistence.projection;

import java.util.Date;

/**
 * @author : prateekgoel
 * @since : 23/12/20, Wed
 **/
public interface WorkflowDocuments {

    Long getDocumentId();

    String getDocumentName();

    Date getUploadedOn();

    Long getUserId();

    Long getDocumentTypeId();

    String getNotes();

    String getExtension();

    Long getSizeKb();

    Long getWorkflowId();

    String getUploadRefId();

    String getUploadedBy();

    String getDocumentType();

}
