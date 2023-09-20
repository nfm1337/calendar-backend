package ru.nfm.calendar.model;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Embeddable
@Data
public class CalendarUserKey implements Serializable {
    private Integer userId;
    private Integer calendarId;
}
