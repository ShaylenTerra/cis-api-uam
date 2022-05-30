package com.dw.ngms.cis.persistence.repository.prepackage;

import com.dw.ngms.cis.persistence.domains.prepackage.PrePackageSubscription;
import com.dw.ngms.cis.persistence.projection.prepackage.PrepackageSubscriptionProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author : prateekgoel
 * @since : 04/01/21, Mon
 **/
@Repository
public interface PrePackageSubscriptionRepository extends JpaRepository<PrePackageSubscription, Long> {

    Page<PrePackageSubscription> findAllByUserId(final Long userId, final Pageable pageable);

    @Modifying
    @Query(" update PrePackageSubscription  pps set pps.subscriptionStatus = :subscriptionStatus where pps.userId =:userId and pps.subscriptionId =:subscriptionId")
    int updateSubscriptionStatus(final Long userId, final Long subscriptionId, final Integer subscriptionStatus);

    @Query(value = "SELECT\n" +
            "    PPS.SUBSCRIPTION_ID as subscriptionId,\n" +
            "    PPS.LOCATION as locationName,\n" +
            "    PPS.SUBSCRIPTION_STATUS as subscriptionStatus,\n" +
            "    PPS.SUBSCRIPTION_DATE as subscriptionDate,\n" +
            "    PPS.REFRENCENO as referenceNo,\n" +
            "    MLI.CAPTION as location,\n" +
            "    PPS.FREQUENCY_ID as frequency,\n" +
            "    MLII.CAPTION as type, \n" +
            "    PPC.NAME as subscriptionName,\n" +
            "    PPC.DESCRIPTION as description\n" +
            "    FROM PRE_PACKAGE_SUBSCRIPTION PPS\n" +
            "INNER JOIN M_LIST_ITEM MLI on MLI.ITEMID = PPS.LOCATION_TYPE_ID\n" +
            "INNER JOIN PRE_PACKAGE_CONFIGURATION PPC on PPC.PRE_PACKAGE_ID = PPS.PRE_PACKAGE_ID\n" +
            "INNER JOIN M_LIST_ITEM MLII on MLII.ITEMID = PPC.PRE_PACKAGE_DATA_TYPE\n" +
            "INNER JOIN  USERS U on U.USERID = PPS.USERID\n" +
            "WHERE PPS.USERID = :userId", nativeQuery = true)
    Page<PrepackageSubscriptionProjection> getAllPrepackageSubscription(final Long userId, final Pageable pageable);

    Collection<PrePackageSubscription> findAllBySubscriptionStatus(final Integer subscriptionStatus);

    Collection<PrePackageSubscription> findAllBySubscriptionStatusAndSubscriptionId(final Integer subscriptionStatus,
                                                                                    final Long subscriptionId);

    Collection<PrePackageSubscription> findAllBySubscriptionStatusAndProcessData(final Integer subscriptionStatus,
                                                                                 final Integer processData);

    Collection<PrePackageSubscription> findAllByUserIdAndFrequencyIdAndPrePackageIdAndLocationTypeId(final Long userId,
                                                                                                         final String frequencyId,
                                                                                                         final Long prepackageId,
                                                                                                         final Long locationTypeId);


}
