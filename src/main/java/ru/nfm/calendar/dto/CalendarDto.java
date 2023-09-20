package ru.nfm.calendar.dto;

import ru.nfm.calendar.model.CalendarColor;

public record CalendarDto(
        String title,
        CalendarColor color
) {
}
