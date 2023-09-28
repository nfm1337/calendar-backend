package ru.nfm.calendar.service;

import ru.nfm.calendar.dto.CalendarDto;
import ru.nfm.calendar.model.Calendar;
import ru.nfm.calendar.model.User;
import ru.nfm.calendar.payload.request.CalendarRequest;

import java.util.List;

public interface CalendarService {

    Calendar createCalendar(User user, CalendarRequest calendarRequest);

    List<CalendarDto> getUserCalendars(User user);

    boolean toggleIsActive(User user, int id);
}
