package com.dw.ngms.cis.persistence.repository.report;

import com.dw.ngms.cis.persistence.domains.report.Reports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author : prateekgoel
 * @since : 15/06/21, Tue
 **/
@Repository
public interface ReportsRepository extends JpaRepository<Reports, Long> {

    @Query(value = "SELECT RPT.* FROM M_REPORTS RPT\n" +
            "    LEFT OUTER JOIN M_REPORTS_ROLE rr on RPT.report_id = rr.report_id\n" +
            "where rr.role_id in (:roleIds) and RPT.module_id= :reportModuleId order by RPT.REPORT_NAME",
            nativeQuery = true)
    Collection<Reports> getAllReportsByRoleId(final Collection<Long> roleIds, final Long reportModuleId);
}
