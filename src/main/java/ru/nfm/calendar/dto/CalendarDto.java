package ru.nfm.calendar.dto;

import ru.nfm.calendar.model.CalendarColor;
import ru.nfm.calendar.util.validation.NoHtml;

public record CalendarDto(
        Integer id,
        @NoHtml
        String title,
        CalendarColor color,
        Boolean isActive
) {
}
