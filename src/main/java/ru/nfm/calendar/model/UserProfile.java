package ru.nfm.calendar.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "user_profile")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id")
    @MapsId
    private User user;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "sur_name", length = 50)
    private String surName;

    @Column(name = "company_name", length = 100)
    private String companyName;

    @Column(name = "position", length = 50)
    private String position;

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
