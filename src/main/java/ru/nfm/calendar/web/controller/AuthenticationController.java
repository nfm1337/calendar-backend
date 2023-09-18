package ru.nfm.calendar.web.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nfm.calendar.payload.request.RefreshTokenRequest;
import ru.nfm.calendar.payload.request.SignInRequest;
import ru.nfm.calendar.payload.request.SignUpRequest;
import ru.nfm.calendar.payload.response.JwtAuthenticationResponse;
import ru.nfm.calendar.service.AuthenticationService;

@RestController
@RequestMapping(path = "/api/v1/auth", consumes = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    public ResponseEntity<JwtAuthenticationResponse> signUp(@Valid @RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signUp(request));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@Valid @RequestBody SignInRequest request) {
        log.debug("SignIn: " + request);
        return ResponseEntity.ok(authenticationService.signIn(request));
    }

    @PostMapping("/sign-out")
    public ResponseEntity<String> signOut() {
        return ResponseEntity.ok("Logged out");
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<JwtAuthenticationResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(authenticationService.refreshToken(request));
    }
}
