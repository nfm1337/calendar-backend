package ru.nfm.calendar.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nfm.calendar.dto.CalendarDto;
import ru.nfm.calendar.mapper.CalendarUserMapper;
import ru.nfm.calendar.model.*;
import ru.nfm.calendar.payload.request.CalendarRequest;
import ru.nfm.calendar.repository.CalendarRepository;
import ru.nfm.calendar.repository.CalendarUserRepository;
import ru.nfm.calendar.repository.UserProfileRepository;
import ru.nfm.calendar.service.CalendarService;
import ru.nfm.calendar.util.SecurityUtil;

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
    public Calendar createCalendar(CalendarRequest calendarRequest) {
        String email = SecurityUtil.getUserDetails().getUsername();
        UserProfile userProfile = userProfileRepository.getExistedByEmail(email);

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

        calendarUserRepository.save(calendarUser);

        return calendar;
    }

    @Override
    @Transactional
    public List<CalendarDto> getUserCalendars() {
        String email = SecurityUtil.getUserDetails().getUsername();
        UserProfile userProfile = userProfileRepository.getExistedByEmail(email);

        return userProfile.getCalendarUserList().stream()
                .map(user -> calendarUserMapper.toDto(user.getCalendar(), user.getIsCalendarActive()))
                .toList();
    }

    @Override
    @Transactional
    public boolean toggleIsActive(int id) {
        String email = SecurityUtil.getUserDetails().getUsername();
        UserProfile userProfile = userProfileRepository.getExistedByEmail(email);

        CalendarUser calendarUser = userProfile.getCalendarUserList().stream()
                .filter(user -> user.getCalendar().getId().equals(id))
                .findFirst()
                .orElse(null);

        if (calendarUser != null) {
            calendarUser.setIsCalendarActive(!calendarUser.getIsCalendarActive());
            calendarUserRepository.save(calendarUser);

            return calendarUser.getIsCalendarActive();
        }

        throw new AccessDeniedException("");
    }
}
