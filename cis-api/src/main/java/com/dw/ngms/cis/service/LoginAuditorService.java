package com.dw.ngms.cis.service;

import com.dw.ngms.cis.persistence.domains.LoginAudit;
import com.dw.ngms.cis.persistence.domains.User;
import com.dw.ngms.cis.persistence.repository.LoginAuditRepository;
import com.dw.ngms.cis.service.event.GenericNotificationEvent;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Date;

/**
 * @author : prateekgoel
 * @since : 24/11/20, Tue
 **/
@Service
@AllArgsConstructor
@Slf4j
public class LoginAuditorService {

    private final LoginAuditRepository loginAuditRepository;

    @Async
    @EventListener(condition = "#event.eventType.name() == 'USER_LOGGED_IN_SUCCESSFULLY'")
    public void onLoggedInSuccessfullyEvent(@NotNull final GenericNotificationEvent<User> event) {
        log.debug(" user {} login event occur !! ", event.getContent());
        User content = event.getContent();

        LoginAudit loginAudit = LoginAudit.builder()
                .timestamp(Date.from(Instant.now()))
                .userType(content.getUserTypeItemId())
                .username(content.getUserName()).build();
        LoginAudit savedLoginAudit = loginAuditRepository.save(loginAudit);
        log.debug(" loginAudit {} event saved successfully !!", savedLoginAudit);
    }

}
