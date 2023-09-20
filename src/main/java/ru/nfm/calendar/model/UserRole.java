package ru.nfm.calendar.model;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    ADMIN, USER, MODERATOR, EMAIL_NOT_CONFIRMED;

    @Override
    public String getAuthority() {
        return "ROLE_" + name();
    }
}
