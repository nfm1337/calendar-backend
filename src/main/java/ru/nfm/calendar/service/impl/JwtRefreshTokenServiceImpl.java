package ru.nfm.calendar.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.nfm.calendar.exception.TokenRefreshException;
import ru.nfm.calendar.exception.UserNotFoundException;
import ru.nfm.calendar.model.RefreshToken;
import ru.nfm.calendar.model.User;
import ru.nfm.calendar.repository.RefreshTokenRepository;
import ru.nfm.calendar.repository.UserRepository;
import ru.nfm.calendar.service.JwtRefreshTokenService;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JwtRefreshTokenServiceImpl implements JwtRefreshTokenService {

    @Value("${jwt.refreshToken.expiration}")
    private long jwtRefreshTokenExpirationTimeMillis;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public RefreshToken createRefreshToken(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId, "User not found"));
        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .expiresAt(Instant.now().plusMillis(jwtRefreshTokenExpirationTimeMillis))
                .token(UUID.randomUUID().toString())
                .build();

        refreshTokenRepository.deleteByUser(user);
        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    @Override
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiresAt().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(
                    token.getToken(), "Refresh token expired. Please make a new sign in request");
        }
        return token;
    }
}
