package ru.nfm.calendar.payload.response;

public record CalendarCreateResponse(
        int id,
        int creatorId,
        String title,
        String message
) {
}
