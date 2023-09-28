package ru.nfm.calendar.dto;

import java.time.Instant;

public record CalendarEventDto(
        String title,
        String description,
        Instant timeFrom,
        Instant timeTo,
        Instant notificationTime,
        Boolean isBlocking
) {
}
