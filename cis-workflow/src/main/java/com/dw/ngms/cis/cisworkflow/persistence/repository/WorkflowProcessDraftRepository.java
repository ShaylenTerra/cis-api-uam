package com.dw.ngms.cis.cisworkflow.persistence.repository;

import com.dw.ngms.cis.cisworkflow.persistence.domain.WorkflowProcessDraft;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author : prateekgoel
 * @since : 20/05/21, Thu
 **/
@Repository
public interface WorkflowProcessDraftRepository extends JpaRepository<WorkflowProcessDraft, Long> {

    @Modifying(flushAutomatically = true)
    @Transactional
    @Query("Update WorkflowProcessDraft t SET t.configuration=:configuration, " +
            "t.designData=:designData, t.draftVersion=:draftVersion, " +
            "t.name=:name, t.description=:description  " +
            "WHERE t.processId=:processId")
    void updateWorkflowProcessDraft(@Param("processId") Long paramLong,
                                    @Param("name") String paramString1,
                                    @Param("description") String paramString2,
                                    @Param("configuration") String paramString3,
                                    @Param("designData") String paramString4,
                                    @Param("draftVersion") BigDecimal paramBigDecimal);

    @Modifying(flushAutomatically = true)
    @Transactional
    @Query("Update WorkflowProcessDraft t SET t.publishedVersion=:publishedVersion WHERE t.processId=:processId")
    void updatePublishedVersionDraft(@Param("processId") long paramLong, @Param("publishedVersion") BigDecimal paramBigDecimal);

    @Modifying
    @Transactional
    @Query("DELETE FROM WorkflowProcessDraft t WHERE t.parentProcessId=:parentProcessId")
    void deletePublishedItem(@Param("parentProcessId") long paramLong);

    @Query("SELECT max(t.processId)+1 FROM WorkflowProcessDraft t")
    Long getProcessId();

    @Query("SELECT  t.name, t.description, t.provinceId, t.publishedVersion, " +
            "t.dated, t.draftVersion, t.processId FROM WorkflowProcessDraft t" +
            " WHERE t.provinceId=:provinceId AND t.itemIdModule=:itemIdModule")
    List<Object[]> findByItemIdModuleAndProvinceId(@Param("provinceId") long paramLong, @Param("itemIdModule") int paramInt);

    @Query("SELECT  t.name, t.description, t.provinceId, t.publishedVersion, " +
            "t.dated, t.draftVersion, t.processId FROM WorkflowProcessDraft t " +
            "WHERE t.itemIdModule=:itemIdModule")
    List<Object[]> findByItemIdModule(@Param("itemIdModule") int paramInt);

    @Query("SELECT  t.name, t.description, t.provinceId, t.publishedVersion," +
            " t.dated, t.draftVersion, t.processId FROM WorkflowProcessDraft t" +
            " WHERE t.provinceId=:provinceId")
    List<Object[]> findByProvinceId(@Param("provinceId") long paramLong);

    @Query("SELECT  t.name, t.description, t.provinceId, t.publishedVersion," +
            " t.dated, t.draftVersion, t.processId FROM WorkflowProcessDraft t")
    List<Object[]> findByAll();

    @Query("SELECT t.name, t.description, t.provinceId, t.publishedVersion, " +
            "t.dated, t.draftVersion, t.processId,  t.itemIdModule, t.configuration," +
            " t.designData FROM WorkflowProcessDraft t WHERE t.processId=:processId")
    List<Object[]> findByProcessId(@Param("processId") long paramLong);

    @Query("Select t from WorkflowProcessDraft t where t.processId =:processId")
    WorkflowProcessDraft findUsingProcessId(final Long processId);


}
