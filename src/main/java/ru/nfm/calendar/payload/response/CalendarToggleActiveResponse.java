package ru.nfm.calendar.payload.response;

public record CalendarToggleActiveResponse(
        int calendarId,
        boolean isActive
) {
}
