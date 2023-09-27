package ru.nfm.calendar.service;

import ru.nfm.calendar.dto.CalendarEventDto;

public interface CalendarEventService {

    CalendarEventDto createCalendarEvent(int calendarId, CalendarEventDto calendarEventDto);
}
