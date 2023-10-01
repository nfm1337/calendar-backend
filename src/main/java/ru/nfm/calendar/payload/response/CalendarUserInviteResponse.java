package ru.nfm.calendar.payload.response;

public record CalendarUserInviteResponse(
        int userId,
        int calendarId,
        String message
) {
}
