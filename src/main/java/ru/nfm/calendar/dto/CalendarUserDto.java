package ru.nfm.calendar.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.nfm.calendar.model.CalendarRole;

public record CalendarUserDto(
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        int userId,
        String email,
        String firstName,
        String lastName,
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        CalendarRole calendarRole
) {
}
