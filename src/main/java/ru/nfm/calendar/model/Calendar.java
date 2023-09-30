package ru.nfm.calendar.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Table(name = "calendar")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Calendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "creator_id", updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserProfile creator;

    @Column(name = "title", length = 256, nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "color", length = 256, nullable = false)
    private CalendarColor color;

    @OneToMany(mappedBy = "calendar", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<CalendarEvent> events;

    @OneToMany(mappedBy = "calendar", fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<CalendarUser> calendarUsers;
}
