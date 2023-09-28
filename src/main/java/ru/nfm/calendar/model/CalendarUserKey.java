package ru.nfm.calendar.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Embeddable
@Data
public class CalendarUserKey implements Serializable {
    @Serial
    private static final long serialVersionUID = 12412412;
    private Integer userId;
    private Integer calendarId;
}
