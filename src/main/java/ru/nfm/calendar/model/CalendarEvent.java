package ru.nfm.calendar.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "calendar_event")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CalendarEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "attachment",
            joinColumns = @JoinColumn(name = "calendar_event_id"),
            inverseJoinColumns = @JoinColumn(name = "id")
    )
    @JoinColumn(name = "id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Attachment> attachments;

    @JoinTable(name = "calendar_event_user",
            joinColumns = @JoinColumn(name = "calendar_event_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @ManyToMany(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<UserProfile> attachedUsers;

    public void addAttachedUser(UserProfile userProfile) {
        if (attachedUsers == null) {
            attachedUsers = new ArrayList<>();
        }
        attachedUsers.add(userProfile);
    }
}
