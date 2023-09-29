package ru.nfm.calendar.service;

import ru.nfm.calendar.dto.CalendarEventDto;
import ru.nfm.calendar.model.User;

import java.time.LocalDateTime;
import java.util.List;

public interface CalendarEventService {
    CalendarEventDto createCalendarEvent(User user, int calendarId, CalendarEventDto calendarEventDto);
    CalendarEventDto updateCalendarEvent(User user, int calendarId, int eventId, CalendarEventDto calendarEventDto);
    CalendarEventDto getCalendarEvent(User user, int calendarId, int eventId);
    List<CalendarEventDto> getCalendarEventsByDateTimeRange(
            User user, int calendarId, LocalDateTime dtFrom, LocalDateTime dtTo);
    List<CalendarEventDto> getUserAttachedCalendarEventsByDateTimeRange(
            User user, int calendarId, LocalDateTime dtFrom, LocalDateTime dtTo);
}
