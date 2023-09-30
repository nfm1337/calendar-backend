package ru.nfm.calendar.payload.request;

import ru.nfm.calendar.model.CalendarColor;
import ru.nfm.calendar.util.validation.nohtml.NoHtml;

public record CalendarRequest(
        @NoHtml
        String title,
        CalendarColor color
) {
}