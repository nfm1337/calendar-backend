package ru.nfm.calendar.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CalendarUserKey implements Serializable {

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "calendar_id")
    private Integer calendarId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CalendarUserKey that)) return false;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(calendarId, that.calendarId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, calendarId);
    }
}
