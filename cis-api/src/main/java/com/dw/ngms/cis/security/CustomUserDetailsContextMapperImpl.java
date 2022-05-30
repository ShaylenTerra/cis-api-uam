package com.dw.ngms.cis.security;

import com.dw.ngms.cis.persistence.domains.Roles;
import com.dw.ngms.cis.persistence.domains.User;
import com.dw.ngms.cis.persistence.domains.user.SecurityUser;
import com.dw.ngms.cis.persistence.repository.RoleRepository;
import com.dw.ngms.cis.persistence.repository.UserRepository;
import com.dw.ngms.cis.utilities.Messages;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author : prateekgoel
 * @since : 30/04/21, Fri
 **/
@Component
@Slf4j
public class CustomUserDetailsContextMapperImpl implements UserDetailsContextMapper {

    private final UserRepository userRepository;

    private final Messages messages;

    private final String passwordAttributeName = "userPassword";

    private final RoleRepository roleRepository;

    private final ObjectMapper objectMapper;

    @Autowired
    public CustomUserDetailsContextMapperImpl(UserRepository userRepository,
                                              Messages messages,
                                              RoleRepository roleRepository,
                                              ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.messages = messages;
        this.roleRepository = roleRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public UserDetails mapUserFromContext(DirContextOperations ctx, String username, Collection<? extends GrantedAuthority> authorities) {
        log.debug("creating security user from ldap");
        User byUserName = userRepository.findByUserNameIgnoreCase(username);
        // if user is not found in db and authenticated by ldap than create a new User Object
        if (null == byUserName) {
            String firstName = (String) ctx.getObjectAttribute("givenname");
            String surname = (String) ctx.getObjectAttribute("sn");
            String email = (String) ctx.getObjectAttribute("mail");
            if (StringUtils.isEmpty(email)) {
                email = (String) ctx.getObjectAttribute("userprincipalname");
            }
            String userName = (String) ctx.getObjectAttribute("samaccountname");

            SecurityUser securityUser = new SecurityUser(firstName, surname, email, userName);

            try {
                throw new UserNotRegisteredException(objectMapper.writeValueAsString(securityUser));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        assert byUserName != null;
        final Collection<SimpleGrantedAuthority> authority = getAuthority(byUserName);
        return new SecurityUser(byUserName, authority);
    }

    @Override
    public void mapUserToContext(UserDetails user, DirContextAdapter ctx) {

    }

    protected Collection<SimpleGrantedAuthority> getAuthority(final User user) {
        return user.getUserRoles().stream().map(userRole -> {
            final Roles byRoleId = roleRepository.findByRoleId(userRole.getRoleId());
            return new SimpleGrantedAuthority(byRoleId.getRoleName());
        }).collect(Collectors.toList());
    }

    protected String mapPassword(Object passwordValue) {
        if (!(passwordValue instanceof String)) {
            // Assume it's binary
            passwordValue = new String((byte[]) passwordValue);
        }
        return (String) passwordValue;

    }
}
