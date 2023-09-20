package ru.nfm.calendar.payload.response;

public record SignUpResponse(
        String accessToken,
        String refreshToken,
        String tokenType,
        int expiresIn,
        String userId,
        String email
) {
}
