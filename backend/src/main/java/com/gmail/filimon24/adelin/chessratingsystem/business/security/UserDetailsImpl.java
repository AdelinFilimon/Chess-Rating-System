package com.gmail.filimon24.adelin.chessratingsystem.business.security;

import com.gmail.filimon24.adelin.chessratingsystem.Constants;
import com.gmail.filimon24.adelin.chessratingsystem.persistence.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class UserDetailsImpl implements UserDetails {

    private final String username;
    private final String password;
    private final Boolean isAdministrator;

    public UserDetailsImpl(UserEntity userEntity) {
        this.username = userEntity.getUsername();
        this.password = userEntity.getPassword();
        this.isAdministrator = userEntity.getIsAdministrator();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (isAdministrator)
            return Arrays.asList(new SimpleGrantedAuthority(Constants.ADMINISTRATOR_ROLE_IDENTIFIER),
                    new SimpleGrantedAuthority(Constants.PLAYER_ROLE_IDENTIFIER));

        return Collections.singletonList(new SimpleGrantedAuthority(Constants.PLAYER_ROLE_IDENTIFIER));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
