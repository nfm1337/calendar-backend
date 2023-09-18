package ru.nfm.calendar.service;

import ru.nfm.calendar.payload.request.RefreshTokenRequest;
import ru.nfm.calendar.payload.request.SignInRequest;
import ru.nfm.calendar.payload.request.SignUpRequest;
import ru.nfm.calendar.payload.response.JwtAuthenticationResponse;

public interface AuthenticationService {

    JwtAuthenticationResponse signUp(SignUpRequest request);

    JwtAuthenticationResponse signIn(SignInRequest request);

    JwtAuthenticationResponse refreshToken(RefreshTokenRequest request);

}
