package com.dw.ngms.cis.persistence.repository.user;

import com.dw.ngms.cis.persistence.domains.user.UserNotification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserNotificationRepository extends JpaRepository<UserNotification,Long> {
    //Get all notifications for logged In user
    Page<UserNotification>  findAllByCreatedForUserIdOrderByIdDesc(final long createdForUserId, final Pageable pageable);
}
