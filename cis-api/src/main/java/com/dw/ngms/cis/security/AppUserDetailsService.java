package com.dw.ngms.cis.security;

import com.dw.ngms.cis.enums.UserStatus;
import com.dw.ngms.cis.persistence.domains.Roles;
import com.dw.ngms.cis.persistence.domains.User;
import com.dw.ngms.cis.persistence.domains.user.SecurityUser;
import com.dw.ngms.cis.persistence.repository.RoleRepository;
import com.dw.ngms.cis.persistence.repository.UserRepository;
import com.dw.ngms.cis.utilities.Messages;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author : prateekgoel
 * @since : 26/04/21, Mon
 **/
@Service
@AllArgsConstructor
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository usersRepository;

    private final RoleRepository roleRepository;

    private final Messages messages;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        final User user = usersRepository.findByUserNameIgnoreCase(userName);
        if (user == null) {
            throw new UsernameNotFoundException(messages
                    .getMessage("AppUserDetailsService.userNotFound", Collections.singletonList(userName)));
        }

        if (user.getStatusId().equals(UserStatus.PENDING.getUserStatus())) {
            throw new UserPendingException(messages
                    .getMessage("AppUserDetailsService.userPendingException", Collections.singletonList(userName)));
        }

        if (user.getStatusId().equals(UserStatus.LOCK.getUserStatus())) {
            throw new UserPendingException(messages
                    .getMessage("AppUserDetailsService.userLockedException", Collections.singletonList(userName)));
        }
        if (user.getStatusId().equals(UserStatus.INACTIVE.getUserStatus())) {
            throw new UserPendingException(messages
                    .getMessage("AppUserDetailsService.userInactiveException", Collections.singletonList(userName)));
        }

        final List<SimpleGrantedAuthority> authorities = user.getUserRoles().stream().map(userRole -> {
            final Roles byRoleId = roleRepository.findByRoleId(userRole.getRoleId());
            return new SimpleGrantedAuthority(byRoleId.getRoleName());
        }).collect(Collectors.toList());

        return new SecurityUser(user, authorities);

    }
}
