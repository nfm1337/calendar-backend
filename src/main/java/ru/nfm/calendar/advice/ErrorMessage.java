package ru.nfm.calendar.advice;

import java.util.Date;

public record ErrorMessage(
        int statusCode,
        Date timestamp,
        Object message,
        String description
) {
}
