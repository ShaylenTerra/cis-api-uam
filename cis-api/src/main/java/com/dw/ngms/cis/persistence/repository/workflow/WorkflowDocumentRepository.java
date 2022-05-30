package com.dw.ngms.cis.persistence.repository.workflow;

import com.dw.ngms.cis.persistence.domains.workflow.WorkflowDocuments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : prateekgoel
 * @since : 30/12/20, Wed
 **/
@Repository
public interface WorkflowDocumentRepository extends JpaRepository<WorkflowDocuments, Long> {

    WorkflowDocuments findWorkflowDocumentsByDocumentIdAndWorkflowId(final Long documentId, final Long workflowId);

    WorkflowDocuments findWorkflowDocumentsByWorkflowIdAndDocumentTypeId(final Long workflowId, final Long documentId);

    List<WorkflowDocuments> findWorkflowDocumentsByWorkflowIdAndDocumentTypeIdIn(final Long workflowId,
                                                                                 final List<Long> documentIds);

    WorkflowDocuments findWorkflowDocumentsByDocumentId(final Long documentId);

    @Query(value = "select\n" +
            "       WFD.WORKFLOWID as workflowId,\n" +
            "       WFD.DOCUMENTID as documentId, \n " +
            "       WFD.DOCNAME as documentName,\n" +
            "       WFD.UPLODEDON as uploadedOn,\n" +
            "       WFD.USERID as userId,\n" +
            "       WFD.DOCUMENTTYPEID as documentTypeId,\n" +
            "       MLI.CAPTION as documentType," +
            "       WFD.NOTES as notes,\n" +
            "       WFD.EXTENSION as extension,\n" +
            "       WFD.SIZE_KB as sizeKb,\n" +
            "       WFD.UPLOAD_REF_ID as uploadRefId,\n" +
            "       CONCAT ( CONCAT(URS.FIRSTNAME, ' ') ,  URS.SURNAME ) as uploadedBy\n" +
            "from WORKFLOW_DOCUMENTS WFD \n" +
            "    INNER JOIN USERS URS on URS.USERID = WFD.USERID\n" +
            "    INNER JOIN M_LIST_ITEM MLI on WFD.DOCUMENTTYPEID = MLI.ITEMID \n" +
            "where WFD.UPLOAD_REF_ID = :cartItemId",
            countQuery = "select count(*) \n" +
                    "from WORKFLOW_DOCUMENTS WFD \n" +
                    "    INNER JOIN USERS URS on URS.USERID = WFD.USERID\n" +
                    "    INNER JOIN M_LIST_ITEM MLI on WFD.DOCUMENTTYPEID = MLI.ITEMID \n" +
                    "where UPLOAD_REF_ID = :cartItemId",
            nativeQuery = true)
    Page<com.dw.ngms.cis.persistence.projection.WorkflowDocuments> getCartItemDocs(final Long cartItemId, final Pageable pageable);


}
