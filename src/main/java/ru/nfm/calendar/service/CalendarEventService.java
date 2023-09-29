package ru.nfm.calendar.service;

import ru.nfm.calendar.dto.CalendarEventDto;
import ru.nfm.calendar.model.CalendarEvent;
import ru.nfm.calendar.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface CalendarEventService {
    CalendarEvent createCalendarEvent(User user, int calendarId, CalendarEventDto calendarEventDto);
    CalendarEvent updateCalendarEvent(User user, int calendarId, int eventId, CalendarEventDto calendarEventDto);
    CalendarEvent getCalendarEvent(User user, int calendarId, int eventId);
    List<CalendarEvent> getCalendarEventsByDateTimeRange(
            User user, int calendarId, LocalDateTime dtFrom, LocalDateTime dtTo);
    List<CalendarEvent> getUserAttachedCalendarEventsByDateTimeRange(
            User user, int calendarId, LocalDateTime dtFrom, LocalDateTime dtTo);
}
