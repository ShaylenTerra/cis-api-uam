package com.dw.ngms.cis.persistence.repository.dashboard;

import com.dw.ngms.cis.persistence.projection.dashboard.InformationRequestManagerAlert;
import com.dw.ngms.cis.persistence.projection.dashboard.InformationRequestNotification;
import com.dw.ngms.cis.persistence.views.VwWfNotifications;
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
public interface VwWfNotificationsRepository extends JpaRepository<VwWfNotifications, String> {


    @Query(value = "Select alrt.REFERENCE_NO as referenceNo,\n" +
            "       alrt.USER_NAME as userName,\n" +
            "       alrt.DATERECEIVED as dated,\n" +
            "       alrt.NOTIFICATION_TYPE as notificationType,\n" +
            "       alrt.INTERNALSTATUSCAPTION as status\n" +
            "from VW_WF_NOTIFICATIONS alrt where alrt.DATA_PROVINCE in (:provinceIds)" +
            " and alrt.DATERECEIVED between :fromDate and :toDate",
            nativeQuery = true)
    Collection<InformationRequestNotification> getInformationRequestNotification(final Collection<Integer> provinceIds,
                                                                                 final LocalDate fromDate,
                                                                                 final LocalDate toDate);

    @Query(value = "Select wfn.NOTIFICATION_TYPE as notificationType,\n" +
            "       count(wfn.REFERENCE_NO) as noOfAlerts\n" +
            "from VW_WF_NOTIFICATIONS wfn where wfn.DATA_PROVINCE in (:provinceIds) " +
            "and wfn.DATERECEIVED between :fromDate and :toDate \n" +
            "group by wfn.NOTIFICATION_TYPE", nativeQuery = true)
    Collection<InformationRequestManagerAlert> getInformationRequestManagerAlerts(final Collection<Integer> provinceIds,
                                                                                  final LocalDate fromDate,
                                                                                  final LocalDate toDate);


}
