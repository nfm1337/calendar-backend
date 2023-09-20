package ru.nfm.calendar.payload.request;

import jakarta.validation.constraints.NotBlank;

public record SignUpRequest(
        @NotBlank(message = "Email should not be blank")
        String email,
        @NotBlank(message = "Password should not be blank")
        String password
) {
}
