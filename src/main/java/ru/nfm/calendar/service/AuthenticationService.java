package ru.nfm.calendar.service;

import ru.nfm.calendar.dto.auth.request.SignInRequest;
import ru.nfm.calendar.dto.auth.request.SignUpRequest;
import ru.nfm.calendar.dto.auth.response.JwtAuthenticationResponse;

public interface AuthenticationService {

    JwtAuthenticationResponse signUp(SignUpRequest request);

    JwtAuthenticationResponse signIn(SignInRequest request);
}
