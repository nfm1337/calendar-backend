package ru.nfm.calendar.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nfm.calendar.dto.CalendarDto;
import ru.nfm.calendar.exception.UserNotFoundException;
import ru.nfm.calendar.mapper.CalendarUserMapper;
import ru.nfm.calendar.model.*;
import ru.nfm.calendar.payload.request.CalendarRequest;
import ru.nfm.calendar.repository.CalendarRepository;
import ru.nfm.calendar.repository.CalendarUserRepository;
import ru.nfm.calendar.repository.UserProfileRepository;
import ru.nfm.calendar.service.CalendarService;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class CalendarServiceImpl implements CalendarService {

    private final CalendarRepository calendarRepository;
    private final UserProfileRepository userProfileRepository;
    private final CalendarUserRepository calendarUserRepository;
    private final CalendarUserMapper calendarUserMapper;

    @Override
    @Transactional
    public Calendar createCalendar(User user, CalendarRequest calendarRequest) {
        UserProfile userProfile = userProfileRepository.findById(user.getId())
                .orElseThrow(() -> new UserNotFoundException(user.getId(), "not found"));

        Calendar calendar = Calendar.builder()
                .color(calendarRequest.color())
                .title(calendarRequest.title())
                .creator(userProfile)
                .build();

        calendar = calendarRepository.save(calendar);

        CalendarUserKey calendarUserKey = new CalendarUserKey();
        calendarUserKey.setUserId(userProfile.getId());
        calendarUserKey.setCalendarId(calendar.getId());
        CalendarUser calendarUser = new CalendarUser();
        calendarUser.setId(calendarUserKey);
        calendarUser.setUserProfile(userProfile);
        calendarUser.setCalendar(calendar);
        calendarUser.setCalendarRole(CalendarRole.CREATOR);
        calendarUser.setIsCalendarActive(true);

        calendarUserRepository.save(calendarUser);

        return calendar;
    }

    @Override
    public List<CalendarDto> getUserCalendars(User user) {
        UserProfile userProfile = userProfileRepository.findByIdWithCalendarUsers(user.getId())
                .orElseThrow(() -> new UserNotFoundException(user.getId(), "not found"));

        return userProfile.getCalendarUserList().stream()
                .map(u -> calendarUserMapper.toDto(u.getCalendar(), u.getIsCalendarActive()))
                .toList();
    }

    @Override
    @Transactional
    public boolean toggleIsActive(User user, int calendarId) {
        CalendarUser calendarUser = calendarUserRepository.findCalendarUserByUserIdAndCalendarId(user.getId(), calendarId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Calendar user with userId: " + user.getId() + " and calendarId: " + calendarId + " not found"));
        calendarUser.setIsCalendarActive(!calendarUser.getIsCalendarActive());
        calendarUserRepository.save(calendarUser);
        return calendarUser.getIsCalendarActive();
    }
}
