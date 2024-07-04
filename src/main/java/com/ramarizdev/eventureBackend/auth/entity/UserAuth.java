package com.ramarizdev.eventureBackend.auth.entity;

import com.ramarizdev.eventureBackend.user.entity.User;
import com.ramarizdev.eventureBackend.user.entity.UserRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
public class UserAuth extends User implements UserDetails {
    private final User user;

    public UserAuth(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(user.getRole().name()));
    }

    public Long getId() {
        return user.getId();
    }

    public String getEmail() {
        return user.getEmail();
    }

    public String getName() {
        if (user.getOrganizer() != null) {
            return user.getOrganizer().getName();
        } else if (user.getAttendee() != null) {
            return user.getAttendee().getName();
        } else {
            return null; // or throw an exception if it should never be null
        }
    }

    public UserRole getRole() {
        return user.getRole();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
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
