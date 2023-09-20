package ru.nfm.calendar.payload.response;

public record JwtAuthenticationResponse(
        String accessToken,
        String refreshToken
) {
}
