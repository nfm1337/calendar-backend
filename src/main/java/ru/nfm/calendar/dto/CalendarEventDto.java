package ru.nfm.calendar.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import ru.nfm.calendar.util.validation.nohtml.NoHtml;

import java.time.LocalDateTime;

public record CalendarEventDto(
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        int id,
        @NotBlank
        @NoHtml
        String title,
        @NoHtml
        String description,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime timeFrom,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime timeTo,
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
        LocalDateTime notificationTime,
        @NotNull
        Boolean isBlocking
) {
}
