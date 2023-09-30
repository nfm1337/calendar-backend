package ru.nfm.calendar.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import ru.nfm.calendar.util.validation.nohtml.NoHtml;
import ru.nfm.calendar.util.validation.timezone.ValidTimezone;

public record UserProfileDto(
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        Integer id,
        @NoHtml
        @JsonProperty(access = JsonProperty.Access.READ_ONLY)
        String email,
        @NoHtml
        @NotBlank(message = "Имя не может быть пустым")
        @Size(max = 64)
        String firstName,
        @NoHtml
        @NotBlank
        @Size(max = 64)
        String lastName,
        @NoHtml
        @Size(max = 64)
        String surName,
        @NoHtml
        String companyName,
        @NoHtml
        String position,
        @NoHtml
        @ValidTimezone
        String timezone) {
}
