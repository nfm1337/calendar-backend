package ru.nfm.calendar.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nfm.calendar.model.Calendar;
import ru.nfm.calendar.model.UserProfile;
import ru.nfm.calendar.payload.request.CalendarRequest;
import ru.nfm.calendar.repository.CalendarRepository;
import ru.nfm.calendar.repository.UserProfileRepository;
import ru.nfm.calendar.service.CalendarService;
import ru.nfm.calendar.util.SecurityUtil;

@Service
@AllArgsConstructor
@Slf4j
public class CalendarServiceImpl implements CalendarService {

    private final CalendarRepository calendarRepository;
    private final UserProfileRepository userProfileRepository;

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

        return calendarRepository.save(calendar);
    }
}
