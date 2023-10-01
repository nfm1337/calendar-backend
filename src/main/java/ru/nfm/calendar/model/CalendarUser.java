package ru.nfm.calendar.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "calendar_user")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CalendarUser {

    @EmbeddedId
    private CalendarUserKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserProfile userProfile;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("calendarId")
    @JoinColumn(name = "calendar_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Calendar calendar;

    @Column(name = "is_calendar_active")
    private Boolean isCalendarActive;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private CalendarRole calendarRole;
}
