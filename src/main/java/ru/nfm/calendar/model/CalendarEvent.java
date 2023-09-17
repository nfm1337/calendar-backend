package ru.nfm.calendar.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "calendar_event")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CalendarEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private UserProfile creator;

    @Column(name = "title", length = 256, nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "time_from")
    private LocalDateTime timeFrom;

    @Column(name = "time_to")
    private LocalDateTime timeTo;

    @Column(name = "notification_time")
    private LocalDateTime notificationTime;

    @Column(name = "is_blocking")
    private Boolean isBlocking;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Attachment> attachments;

    @ManyToMany
    @JoinTable(name = "calendar_event_user")
    private Set<UserProfile> attachedUsers;
}
