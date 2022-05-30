package com.dw.ngms.cis.cisworkflow.persistence.repository;

import com.dw.ngms.cis.cisworkflow.persistence.domain.WorkflowProcess;
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
public interface WorkflowProcessRepository extends JpaRepository<WorkflowProcess, Long> {

    @Modifying
    @Transactional
    @Query("Update WorkflowProcess t SET t.configuration=:configuration, " +
            "t.designData=:designData, t.itemIdModule=:itemIdModule, " +
            "t.draftVersion=:draftVersion, t.publishedVersion=:publishedVersion " +
            "WHERE t.processId=:processId")
    void updateWorkflowProcess(@Param("processId") Long paramLong,
                               @Param("configuration") String paramString1,
                               @Param("designData") String paramString2,
                               @Param("itemIdModule") Integer paramInt,
                               @Param("draftVersion") BigDecimal paramBigDecimal1,
                               @Param("publishedVersion") BigDecimal paramBigDecimal2);

    @Modifying
    @Transactional
    @Query("DELETE FROM WorkflowProcess t WHERE t.parentProcessId=:parentProcessId")
    void deletePublishedItem(@Param("parentProcessId") Long paramLong);

    @Query("SELECT t.name, t.description, t.provinceId, t.publishedVersion, t.dated," +
            " t.draftVersion, t.processId FROM WorkflowProcess t" +
            " WHERE t.provinceId=:provinceId AND t.itemIdModule=:itemIdModule")
    List<Object[]> findByItemIdModuleAndProvinceId(@Param("provinceId") Long paramLong,
                                                   @Param("itemIdModule") Integer paramInt);

    @Query("SELECT t.name, t.description, t.provinceId, t.publishedVersion, " +
            "t.dated, t.draftVersion, t.processId FROM WorkflowProcess t WHERE t.itemIdModule=:itemIdModule ")
    List<Object[]> findByItemIdModule(@Param("itemIdModule") Integer paramInt);

    @Query("SELECT t.name, t.description, t.provinceId, t.publishedVersion, t.dated," +
            " t.draftVersion, t.processId FROM WorkflowProcess t WHERE t.provinceId=:provinceId ")
    List<Object[]> findByProvinceId(@Param("provinceId") Long paramLong);

    @Query("SELECT t.name, t.description, t.provinceId, t.publishedVersion, " +
            "t.dated, t.draftVersion, t.processId FROM WorkflowProcess t")
    List<Object[]> findByAll();

    @Query("SELECT t.name, t.description, t.processId, t.publishedVersion, t.dated, t.draftVersion, " +
            "t.processId, t.itemIdModule, t.configuration, t.designData " +
            "FROM WorkflowProcess t WHERE t.processId=:processId")
    List<Object[]> findByProcessId(@Param("processId") Long paramLong);

    @Query("SELECT t.name, t.description, t.provinceId, t.publishedVersion, t.dated, t.draftVersion, " +
            "t.processId, t.itemIdModule, t.configuration, t.designData FROM WorkflowProcess t" +
            " WHERE t.parentProcessId=:parentProcessId AND t.provinceId=:provinceId")
    List<Object[]> findByProvinceIdAndParentProcessId(@Param("parentProcessId") Long paramLong1,
                                                      @Param("provinceId") Long paramLong2);
}
