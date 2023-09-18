package ru.nfm.calendar.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.nfm.calendar.exception.TokenRefreshException;
import ru.nfm.calendar.model.RefreshToken;
import ru.nfm.calendar.payload.request.RefreshTokenRequest;
import ru.nfm.calendar.payload.request.SignInRequest;
import ru.nfm.calendar.payload.request.SignUpRequest;
import ru.nfm.calendar.payload.response.JwtAuthenticationResponse;
import ru.nfm.calendar.model.User;
import ru.nfm.calendar.model.UserRole;
import ru.nfm.calendar.repository.RefreshTokenRepository;
import ru.nfm.calendar.repository.UserRepository;
import ru.nfm.calendar.service.AuthenticationService;
import ru.nfm.calendar.service.JwtAccessTokenService;
import ru.nfm.calendar.service.JwtRefreshTokenService;

import java.time.Instant;
import java.util.Set;

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
    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        var user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .registeredAt(Instant.now())
                .invalidateTokenBefore(Instant.now())
                .roles(Set.of(UserRole.USER))
                .build();

        userRepository.save(user);
        var jwtAccessToken = jwtAccessTokenService.generateAccessToken(user);
        var jwtRefreshToken = jwtRefreshTokenService.createRefreshToken(user.getId());

        return JwtAuthenticationResponse.builder()
                .accessToken(jwtAccessToken)
                .refreshToken(jwtRefreshToken.getToken())
                .build();
    }

    @Override
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        log.debug("SignIn Request: " + request);
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Invalid email or password"));
        var jwtAccessToken = jwtAccessTokenService.generateAccessToken(user);
        var jwtRefreshToken = jwtRefreshTokenService.createRefreshToken(user.getId());

        return JwtAuthenticationResponse.builder()
                .accessToken(jwtAccessToken)
                .refreshToken(jwtRefreshToken.getToken())
                .build();
    }

    @Override
    public JwtAuthenticationResponse refreshToken(RefreshTokenRequest request) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(request.getRefreshToken())
                .orElseThrow(() -> new TokenRefreshException(request.getRefreshToken(), "Refresh token not found"));
        jwtRefreshTokenService.verifyExpiration(refreshToken);
        User user = userRepository.findById(refreshToken.getUser().getId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        var jwtAccessToken = jwtAccessTokenService.generateAccessToken(user);
        var jwtRefreshToken = jwtRefreshTokenService.createRefreshToken(user.getId());

        return JwtAuthenticationResponse.builder()
                .accessToken(jwtAccessToken)
                .refreshToken(jwtRefreshToken.getToken())
                .build();
    }
}
