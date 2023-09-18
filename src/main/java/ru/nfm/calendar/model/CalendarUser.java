package ru.nfm.calendar.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "calendar_user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CalendarUser {

    @EmbeddedId
    private CalendarUserKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private UserProfile user;

    @ManyToOne
    @MapsId("calendarId")
    @JoinColumn(name = "calendar_id")
    private Calendar calendar;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private CalendarRole calendarRole;
}
