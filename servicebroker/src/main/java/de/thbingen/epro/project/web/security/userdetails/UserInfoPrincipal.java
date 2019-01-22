/**
 * TODO add description
 *
 * @author larsatzinger
 * @author jonashueg
 * @version 1.0
 * @since 1.0
 */

package de.thbingen.epro.project.web.security.userdetails;

import de.thbingen.epro.project.data.model.UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class UserInfoPrincipal implements UserDetails {

    private UserInfo userInfo;

    public UserInfoPrincipal() {
    }

    public UserInfoPrincipal(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return userInfo.getPassword();
    }

    @Override
    public String getUsername() {
        return userInfo.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
