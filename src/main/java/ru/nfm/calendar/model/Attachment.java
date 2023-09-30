package ru.nfm.calendar.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "attachment")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "calendar_event_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CalendarEvent calendarEvent;

    @Column(name = "title")
    private String title;

    @Column(name = "url")
    private String url;
}
