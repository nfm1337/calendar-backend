package ru.nfm.calendar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nfm.calendar.model.Attachment;
import ru.nfm.calendar.model.CalendarEvent;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

    List<Attachment> findAttachmentsByCalendarEvent(CalendarEvent calendarEvent);
}
