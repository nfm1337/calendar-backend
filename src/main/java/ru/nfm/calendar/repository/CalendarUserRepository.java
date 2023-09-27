package ru.nfm.calendar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nfm.calendar.model.CalendarUser;

import java.util.Optional;

@Repository
public interface CalendarUserRepository extends JpaRepository<CalendarUser, Integer> {

    Optional<CalendarUser> findByUserProfileIdAndCalendarId(int userId, int calendarId);
}
