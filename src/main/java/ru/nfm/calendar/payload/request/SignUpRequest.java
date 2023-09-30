package ru.nfm.calendar.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import ru.nfm.calendar.util.validation.nohtml.NoHtml;

public record SignUpRequest(
        @NoHtml
        @NotBlank(message = "Email should not be blank")
        String email,
        @Size(min = 5, max = 32)
        @NotBlank(message = "Password should not be blank")
        String password
) {
}
