package ru.nfm.calendar.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "attachment")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Attachment {

    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "calendar_event_id")
    private CalendarEvent calendarEvent;

    @Column(name = "title")
    private String title;

    @Column(name = "url")
    private String url;
}
