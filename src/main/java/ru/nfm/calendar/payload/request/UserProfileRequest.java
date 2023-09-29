package ru.nfm.calendar.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserProfileRequest(
        @NotBlank(message = "'Имя' - обязательное поле")
        @Size(max = 50, message = "Имя не должно быть длиннее 50 символов")
        String firstName,
        @NotBlank(message = "'Фамилия' - обязательное поле")
        @Size(max = 50, message = "Фамилия не должна быть длиннее 50 символов")
        String lastName,
        @Size(max = 50, message = "Отчество не должно быть длиннее 50 символов")
        String surName,
        @Size(max = 100, message = "Название компании не должно быть длиннее 100 символов")
        String companyName,
        @Size(max = 50, message = "Должность не должна быть длиннее 50 символов")
        String position,
        @NotBlank
        String timezone
) {
}
