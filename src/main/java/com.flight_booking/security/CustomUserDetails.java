package com.flight_booking.security;

import com.flight_booking.user.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private String username;
    private String Password;
    private Collection<GrantedAuthority> authority;

    public CustomUserDetails(User user) {
        this.username = user.getUsername();
        this.Password = user.getPassword();
        this.authority = List.of(
                new SimpleGrantedAuthority("ROLE_" + user.getRol().getName())
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authority;
    }

    @Override
    public String getPassword() {
        return this.Password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }
}
