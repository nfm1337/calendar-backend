package ru.nfm.calendar.util;

import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@UtilityClass
public class DateTimeUtil {

    public Instant convertLocalDateTimeToUtcInstant(String timezone, LocalDateTime localDateTime) {
        ZonedDateTime userZonedDateTime = localDateTime.atZone(ZoneId.of(timezone));
        ZonedDateTime utcZonedDateTime = userZonedDateTime.withZoneSameInstant(ZoneId.of("UTC"));
        return utcZonedDateTime.toInstant();
    }

    public LocalDateTime convertUtcInstantToLocalDateTime(String timezone, Instant instant) {
        return instant.atZone(ZoneId.of(timezone)).toLocalDateTime();
    }
}
