package ru.nfm.calendar.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;

@Entity
@Table(name = "calendar")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Calendar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id", insertable = false, updatable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UserProfile creator;

    @Column(name = "title", length = 256, nullable = false)
    private String title;

    @Enumerated(EnumType.STRING)
    @Column(name = "color", length = 256, nullable = false)
    private CalendarColor color;

    @OneToMany
    private List<CalendarEvent> events;

    @OneToMany
    private List<CalendarUser> calendarUsers;
}
