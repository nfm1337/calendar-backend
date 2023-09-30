package ru.nfm.calendar.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import ru.nfm.calendar.dto.CalendarUserDto;
import ru.nfm.calendar.repository.CalendarUserRepository;
import ru.nfm.calendar.service.CalendarUserService;

import java.util.List;

@Service
@AllArgsConstructor
public class CalendarUserServiceImpl implements CalendarUserService {
    private final CalendarUserRepository calendarUserRepository;

    @Override
    public List<CalendarUserDto> getCalendarUsers(int userId, int calendarId) {
        if (!calendarUserRepository.existsByUserProfileIdAndCalendarId(userId, calendarId)) {
            throw new AccessDeniedException(
                    "User with id: " + userId + " don't have access to calendar with id: " + calendarId);
        }
        return calendarUserRepository.getCalendarUserDtoListByUserIdAndCalendarId(calendarId);
    }
}
