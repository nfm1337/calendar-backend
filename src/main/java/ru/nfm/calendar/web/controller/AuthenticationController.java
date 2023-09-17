package ru.nfm.calendar.web.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nfm.calendar.dto.auth.request.SignInRequest;
import ru.nfm.calendar.dto.auth.request.SignUpRequest;
import ru.nfm.calendar.dto.auth.response.JwtAuthenticationResponse;
import ru.nfm.calendar.service.AuthenticationService;

@RestController
@RequestMapping("/api/v1/auth")
@AllArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signUp")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signUp(request));
    }

    @PostMapping("/signIn")
    public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignInRequest request) {
        return ResponseEntity.ok(authenticationService.signIn(request));
    }
}
