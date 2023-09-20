package ru.nfm.calendar.payload.response;

public record UserProfileSetupResponse(
        String message,
        int userId,
        String email
) {
}
