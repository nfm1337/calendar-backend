package ru.nfm.calendar.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtAccessTokenService {
    String extractUsername(String token);
    String generateAccessToken(UserDetails userDetails);
    boolean isTokenValid(String token, UserDetails userDetails);
}
