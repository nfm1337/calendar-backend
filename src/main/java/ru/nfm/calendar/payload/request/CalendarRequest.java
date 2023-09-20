package ru.nfm.calendar.payload.request;

import ru.nfm.calendar.model.CalendarColor;

public record CalendarRequest(
        String title,
        CalendarColor color
) {
}