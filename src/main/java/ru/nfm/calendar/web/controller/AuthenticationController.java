package ru.nfm.calendar.web.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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
@RequestMapping(value = AuthenticationController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
public class AuthenticationController {

    static final String REST_URL = "/auth";
    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    public ResponseEntity<JwtAuthenticationResponse> signUp(@Valid @RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authenticationService.signUp(request));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@Valid @RequestBody SignInRequest request) {
        return ResponseEntity.ok(authenticationService.signIn(request));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<JwtAuthenticationResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        return ResponseEntity.ok(authenticationService.refreshToken(request));
    }

    @PostMapping("/log-out")
    public ResponseEntity<String> logoutFromAllDevices(HttpServletRequest request) {
        authenticationService.logOutFromAllDevices(request.getHeader("Authorization").substring(7));
        return ResponseEntity.ok("Logged out successfully");
    }
}
