package ru.nfm.calendar.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

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
    private Instant timeFrom;

    @Column(name = "time_to")
    private Instant timeTo;

    @Column(name = "notification_time")
    private Instant notificationTime;

    @Column(name = "is_blocking")
    private Boolean isBlocking;

    @ManyToOne(fetch = FetchType.LAZY)
    private Calendar calendar;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Attachment> attachments;

    @ManyToMany
    private List<UserProfile> attachedUsers;
}
