package com.dw.ngms.cis.persistence.repository.dashboard;

import com.dw.ngms.cis.persistence.projection.dashboard.DashboardMapView;
import com.dw.ngms.cis.persistence.projection.report.UserSummaryProjection;
import com.dw.ngms.cis.persistence.views.VUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;

/**
 * @author : prateekgoel
 * @since : 12/03/21, Fri
 **/
@Repository
public interface VUsersRepository extends JpaRepository<VUsers, Long> {

    @Query(value = "select count(distinct U.EMAIL) from V_USERS U where U.PROVINCE_ID in (:provinceId) " +
            "and U.USERCREATIONDATE between :fromDate and :toDate",
            nativeQuery = true)
    Long findAllDistinctEmails(final Collection<Long> provinceId, final LocalDate fromDate, final LocalDate toDate);

    @Query(value = " select count(U.USERID) from V_USERS U where U.STATUSITEMID = :status and U.PROVINCE_ID in (:provinceId) " +
            "and U.USERCREATIONDATE between :fromDate and :toDate ",
            nativeQuery = true)
    Long findAllByStatus(final Long status, final Collection<Long> provinceId,
                         final LocalDate fromDate, final LocalDate toDate);

    @Query(value = "select count (Distinct WFA.USERID) from VW_WORKFLOW_ACTION WFA " +
            "where WFA.POSTEDON between :fromDate and :toDate and WFA.PROVINCEID in (:provinceId)", nativeQuery = true)
    Long findActiveParticipatingUsers(final Collection<Long> provinceId,
                                      final LocalDate fromDate, final LocalDate toDate);

    @Query(value = "SELECT\n" +
            "    work.DATA_PROVINCE_NAME as dataProvinceName,\n" +
            "    CRT.item as productName,\n" +
            "    count(1) as totalProduct\n" +
            "FROM\n" +
            "    CART_ITEMS CRT\n" +
            "inner join VW_WORKFLOW_PRODUCTIVITY work on CRT.WORKFLOWID = work.WORKFLOWID\n" +
            "where work.INTERNALSTATUS in(7,14)\n" +
            "group by  CRT.item,work.DATA_PROVINCE_NAME", nativeQuery = true)
    Collection<DashboardMapView> getDashboardMapView();

    Collection<VUsers> findByProvinceIdInAndUserCreationDateBetween(final Collection<Long> provinceIds,
                                                                    final LocalDate fromDate,
                                                                    final LocalDate toDate);


}
