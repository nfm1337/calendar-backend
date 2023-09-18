package ru.nfm.calendar.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "user_profile")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserProfile {

    @Id
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    @MapsId
    private User user;

    @Column(name = "first_name", nullable = false, length = 64)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 64)
    private String lastName;

    @Column(name = "sur_name", length = 64)
    private String surName;

    @Column(name = "company_name", length = 256)
    private String companyName;

    @Column(name = "position", length = 128)
    private String position;

    @Column(name = "telegram_chat_id")
    private long telegramChatId;

    @Column(name = "time_zone")
    private String timezone;

    @OneToMany(fetch = FetchType.LAZY)
    private List<Calendar> createdCalendars;

    @ManyToMany
    @JoinTable(name = "calendar_event_user",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "calendar_event_id")
    )
    private List<CalendarEvent> attachedEvents;
}
