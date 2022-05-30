package com.dw.ngms.cis.security;

import com.dw.ngms.cis.persistence.domains.user.SecurityUser;
import com.dw.ngms.cis.persistence.domains.user.UserLoginHistory;
import com.dw.ngms.cis.persistence.repository.user.UserLoginHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.security.AbstractAuthenticationAuditListener;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : prateekgoel
 * @since : 15/06/21, Tue
 **/
@Component
@Slf4j
public class AuthenticationAuditListener {

    private final UserLoginHistoryRepository userLoginHistoryRepository;

    @Autowired
    public AuthenticationAuditListener(UserLoginHistoryRepository userLoginHistoryRepository) {
        this.userLoginHistoryRepository = userLoginHistoryRepository;
    }


    @Async
    @EventListener({AuthenticationSuccessEvent.class})
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        AbstractAuthenticationToken auth = (AbstractAuthenticationToken) event
                .getSource();
        final SecurityUser principal = (SecurityUser) auth.getPrincipal();
        UserLoginHistory userLoginHistory = new UserLoginHistory();
        userLoginHistory.setUserId(principal.getUserId());
        userLoginHistory.setTokenId(principal.getUserId());
        userLoginHistory.setCreatedDate(LocalDateTime.now());
        userLoginHistoryRepository.save(userLoginHistory);
        log.info("User connected: {}", auth.getName());
    }
}
