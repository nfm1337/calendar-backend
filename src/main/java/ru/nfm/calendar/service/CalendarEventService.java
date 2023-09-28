package ru.nfm.calendar.service;

import ru.nfm.calendar.dto.CalendarEventDto;
import ru.nfm.calendar.model.CalendarEvent;
import ru.nfm.calendar.model.User;

public interface CalendarEventService {

    CalendarEvent createCalendarEvent(User user, int calendarId, CalendarEventDto calendarEventDto);

    CalendarEvent updateCalendarEvent(User user, int calendarId, int eventId, CalendarEventDto calendarEventDto);

    CalendarEvent getCalendarEvent(User user, int calendarId, int eventId);
}
