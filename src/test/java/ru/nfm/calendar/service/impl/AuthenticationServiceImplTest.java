package ru.nfm.calendar.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.nfm.calendar.model.RefreshToken;
import ru.nfm.calendar.model.User;
import ru.nfm.calendar.payload.request.RefreshTokenRequest;
import ru.nfm.calendar.payload.request.SignInRequest;
import ru.nfm.calendar.payload.request.SignUpRequest;
import ru.nfm.calendar.payload.response.JwtAuthenticationResponse;
import ru.nfm.calendar.repository.RefreshTokenRepository;
import ru.nfm.calendar.repository.UserRepository;
import ru.nfm.calendar.service.JwtAccessTokenService;
import ru.nfm.calendar.service.JwtRefreshTokenService;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AuthenticationServiceImplTest {

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private RefreshTokenRepository refreshTokenRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtAccessTokenService jwtAccessTokenService;
    @Mock
    private JwtRefreshTokenService jwtRefreshTokenService;
    @Mock
    private AuthenticationManager authenticationManager;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSignUp() {
        SignUpRequest signUpRequest = new SignUpRequest("test@example.com", "password123");
        User savedUser = new User();
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(jwtAccessTokenService.generateAccessToken(any(User.class))).thenReturn("access-token");
        when(jwtRefreshTokenService.createRefreshToken(any(User.class))).thenReturn(createRefreshToken());
        JwtAuthenticationResponse response = authenticationService.signUp(signUpRequest);
        assertEquals("access-token", response.accessToken());
    }

    @Test
    void signIn() {
        SignInRequest signInRequest = new SignInRequest("test@example.com", "password123");
        User existingUser = new User();
        existingUser.setPassword("hashed-password");
        when(userRepository.getExistedByEmail(anyString())).thenReturn(existingUser);
        when(jwtAccessTokenService.generateAccessToken(any(User.class))).thenReturn("access-token");
        when(jwtRefreshTokenService.createRefreshToken(any(User.class))).thenReturn(new RefreshToken());

        JwtAuthenticationResponse response = authenticationService.signIn(signInRequest);
        assertEquals("access-token", response.accessToken());
    }

    @Test
    public void testRefreshTokenUserNotFound() {
        RefreshTokenRequest refreshTokenRequest = new RefreshTokenRequest("refresh-token");
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(new User());
        when(refreshTokenRepository.findByToken(anyString())).thenReturn(Optional.of(refreshToken));
        when(userRepository.findById(1)).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> authenticationService.refreshToken(refreshTokenRequest));
    }

    @Test
    void logOutFromAllDevices() {
        User user = new User();
        user.setInvalidateTokenBefore(Instant.now());
        Mockito.doNothing().when(refreshTokenRepository).deleteByUser(any(User.class));
        authenticationService.logOutFromAllDevices(user);
        verify(refreshTokenRepository, Mockito.times(1)).deleteByUser(user);
    }

    private RefreshToken createRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken("refresh-token");
        refreshToken.setExpiresAt(Instant.now().plusSeconds(3600)); // Expires in 1 hour
        return refreshToken;
    }
}