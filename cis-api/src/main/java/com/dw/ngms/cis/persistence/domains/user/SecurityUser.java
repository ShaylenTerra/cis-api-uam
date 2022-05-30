package com.dw.ngms.cis.persistence.domains.user;

import com.dw.ngms.cis.persistence.domains.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author : prateekgoel
 * @since : 26/04/21, Mon
 **/

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SecurityUser extends User implements UserDetails {

    private Collection<SimpleGrantedAuthority> authorities;


    public SecurityUser(User user, Collection<SimpleGrantedAuthority> authorities) {

        super(user.getUserId(), user.getUserCode(), user.getUserName(), user.getUserTypeItemId(), user.getPassword(), user.getTempPassword()
                , user.getFirstName(), user.getSurname(), user.getMobileNo(), user.getTelephoneNo(), user.getEmail(),
                user.getStatusId(), user.getCreatedDate(), user.getLastUpdatedDate(), user.getCountryCode(), user.getTitleItemId(),
                user.getProvinceId(),user.getResetPassword(), user.getUserMetaData(), user.getUserRoles());
        this.authorities = authorities;

    }



    public SecurityUser(String firstName, String lastName,String email, String userName) {
        super(firstName,lastName,email,userName);

    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;

    }

    @Override
    public String getUsername() {
        return this.getUserName();
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}
