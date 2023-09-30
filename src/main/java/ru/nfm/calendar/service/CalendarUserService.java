package ru.nfm.calendar.service;

import ru.nfm.calendar.dto.CalendarUserDto;

import java.util.List;

public interface CalendarUserService {

    List<CalendarUserDto> getCalendarUsers(int userId, int calendarId);


}
