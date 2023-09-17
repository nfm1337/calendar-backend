package ru.nfm.calendar.model;

import org.springframework.security.core.GrantedAuthority;


public enum UserRole implements GrantedAuthority {
    ADMIN, USER, EDITOR;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}
