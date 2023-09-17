package ru.nfm.calendar.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.Set;

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

    @Column(name = "color", length = 256, nullable = false)
    private String color;

    @OneToMany
    private Set<CalendarEvent> events;
}
