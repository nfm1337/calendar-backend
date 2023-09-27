package ru.nfm.calendar.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nfm.calendar.dto.CalendarEventDto;
import ru.nfm.calendar.mapper.CalendarEventMapper;
import ru.nfm.calendar.model.CalendarEvent;
import ru.nfm.calendar.model.CalendarRole;
import ru.nfm.calendar.model.CalendarUser;
import ru.nfm.calendar.model.User;
import ru.nfm.calendar.repository.CalendarEventRepository;
import ru.nfm.calendar.repository.CalendarRepository;
import ru.nfm.calendar.repository.CalendarUserRepository;
import ru.nfm.calendar.repository.UserRepository;
import ru.nfm.calendar.service.CalendarEventService;
import ru.nfm.calendar.util.SecurityUtil;

@Service
@AllArgsConstructor
public class CalendarEventServiceImpl implements CalendarEventService {

    private final UserRepository userRepository;
    private final CalendarRepository calendarRepository;
    private final CalendarUserRepository calendarUserRepository;
    private final CalendarEventRepository calendarEventRepository;
    private final CalendarEventMapper calendarEventMapper;

    @Override
    @Transactional
    public CalendarEventDto createCalendarEvent(int calendarId, CalendarEventDto calendarEventDto) {
        String email = SecurityUtil.getUserDetails().getUsername();
        User user = userRepository.getExistedByEmail(email);

        CalendarUser calendarUser = calendarUserRepository.findByUserProfileIdAndCalendarId(user.getId(), calendarId)
                .orElseThrow(EntityNotFoundException::new);

        if (calendarUser.getCalendarRole() == CalendarRole.USER) {
            throw new AccessDeniedException("Нет прав на создание событий");
        }

        CalendarEvent event = CalendarEvent.builder()
                .title(calendarEventDto.title())
                .description(calendarEventDto.description())
                .creator(user.getUserProfile())
                .calendar(calendarUser.getCalendar())
                .timeFrom(calendarEventDto.timeFrom())
                .timeTo(calendarEventDto.timeTo())
                .isBlocking(calendarEventDto.isBlocking())
                .notificationTime(calendarEventDto.notificationTime())
                .build();

        event.addAttachedUser(user.getUserProfile());

        return calendarEventMapper.toDto(calendarEventRepository.save(event));
    }
}
