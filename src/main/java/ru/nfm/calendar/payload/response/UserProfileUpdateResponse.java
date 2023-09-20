package ru.nfm.calendar.payload.response;

import ru.nfm.calendar.dto.UserProfileDto;

public record UserProfileUpdateResponse(
        String message,
        UserProfileDto updatedProfile
) {
}
