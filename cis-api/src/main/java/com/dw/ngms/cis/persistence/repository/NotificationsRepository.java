package com.dw.ngms.cis.persistence.repository;

import com.dw.ngms.cis.persistence.domains.Notifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author : prateekgoel
 * @since : 29/12/20, Tue
 **/
@Repository
public interface NotificationsRepository extends JpaRepository<Notifications, Long> {

    Page<Notifications> findByNotificationUserTypesItemId(final Long notificationUserTypesItemId,
                                                          final Pageable pageable);

}
