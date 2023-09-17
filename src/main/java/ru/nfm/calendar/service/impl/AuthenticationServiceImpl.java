package ru.nfm.calendar.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.nfm.calendar.dto.auth.request.SignInRequest;
import ru.nfm.calendar.dto.auth.request.SignUpRequest;
import ru.nfm.calendar.dto.auth.response.JwtAuthenticationResponse;
import ru.nfm.calendar.model.User;
import ru.nfm.calendar.model.UserRole;
import ru.nfm.calendar.repository.UserRepository;
import ru.nfm.calendar.service.AuthenticationService;
import ru.nfm.calendar.service.JwtService;

import java.util.Set;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        var user = User.builder()
                .login(request.getLogin())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(Set.of(UserRole.USER))
                .build();
        userRepository.save(user);
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }

    @Override
    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword()));
        var user = userRepository.findByEmail(request.getLogin())
                .orElse(userRepository.findByLogin(request.getLogin())
                        .orElseThrow(() -> new UsernameNotFoundException("Invalid login or password")));
        var jwt = jwtService.generateToken(user);
        return JwtAuthenticationResponse.builder().token(jwt).build();
    }
}
