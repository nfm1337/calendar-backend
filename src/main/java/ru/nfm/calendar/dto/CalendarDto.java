package ru.nfm.calendar.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.nfm.calendar.model.CalendarColor;
import ru.nfm.calendar.util.validation.nohtml.NoHtml;

public record CalendarDto(
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Integer id,
        @NoHtml
        String title,
        CalendarColor color,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Boolean isActive
) {
}
