package ru.nfm.calendar.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nfm.calendar.exception.EmailAlreadyExistsException;
import ru.nfm.calendar.exception.TokenRefreshException;
import ru.nfm.calendar.model.RefreshToken;
import ru.nfm.calendar.model.User;
import ru.nfm.calendar.model.UserRole;
import ru.nfm.calendar.payload.request.RefreshTokenRequest;
import ru.nfm.calendar.payload.request.SignInRequest;
import ru.nfm.calendar.payload.request.SignUpRequest;
import ru.nfm.calendar.payload.response.JwtAuthenticationResponse;
import ru.nfm.calendar.repository.RefreshTokenRepository;
import ru.nfm.calendar.repository.UserRepository;
import ru.nfm.calendar.service.AuthenticationService;
import ru.nfm.calendar.service.JwtAccessTokenService;
import ru.nfm.calendar.service.JwtRefreshTokenService;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtAccessTokenService jwtAccessTokenService;
    private final JwtRefreshTokenService jwtRefreshTokenService;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        checkEmailAlreadyExists(request.email());

        var user = User.builder()
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .registeredAt(Instant.now())
                .invalidateTokenBefore(Instant.now())
                .isEmailConfirmed(false)
                .emailConfirmationToken(generateEmailConfirmationToken())
                .roles(Set.of(UserRole.EMAIL_NOT_CONFIRMED))
                .build();

        user = userRepository.save(user);

        var jwtAccessToken = jwtAccessTokenService.generateAccessToken(user);
        var jwtRefreshToken = jwtRefreshTokenService.createRefreshToken(user);

        return new JwtAuthenticationResponse(jwtAccessToken, jwtRefreshToken.getToken());
    }

    @Override
    @Transactional
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        log.debug("SignIn Request: " + request);
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        var user = userRepository.getExistedByEmail(request.email());
        var jwtAccessToken = jwtAccessTokenService.generateAccessToken(user);
        var jwtRefreshToken = jwtRefreshTokenService.createRefreshToken(user);

        return new JwtAuthenticationResponse(jwtAccessToken, jwtRefreshToken.getToken());
    }

    @Override
    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(request.refreshToken())
                .orElseThrow(() -> new TokenRefreshException(request.refreshToken(), "Refresh token not found"));
        jwtRefreshTokenService.verifyExpiration(refreshToken);
        User user = userRepository.findById(refreshToken.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var jwtAccessToken = jwtAccessTokenService.generateAccessToken(user);
        var jwtRefreshToken = jwtRefreshTokenService.createRefreshToken(user);

        return new JwtAuthenticationResponse(jwtAccessToken, jwtRefreshToken.getToken());
    }

    @Override
    public void logOutFromAllDevices(User user) {
        user.setInvalidateTokenBefore(Instant.now());
        refreshTokenRepository.deleteByUser(user);
    }

    private void checkEmailAlreadyExists(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException(email);
        }
    }

    private String generateEmailConfirmationToken() {
        return UUID.randomUUID().toString();
    }
}
