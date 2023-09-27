package ru.nfm.calendar.payload.request;

import jakarta.validation.constraints.NotBlank;
import ru.nfm.calendar.util.validation.NoHtml;

public record SignInRequest(
        @NoHtml
        @NotBlank(message = "Email cannot be blank")
        String email,
        @NotBlank(message = "Password cannot be blank")
        String password
) {
}
