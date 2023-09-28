package ru.nfm.calendar.repository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.nfm.calendar.model.CalendarUser;

import java.util.Optional;

@Repository
public interface CalendarUserRepository extends JpaRepository<CalendarUser, Integer> {

    @Query("SELECT cu FROM CalendarUser cu WHERE cu.userProfile.id = :userId AND cu.calendar.id = :calendarId")
    Optional<CalendarUser> findByUserIdAndCalendarId(int userId, int calendarId);

    default CalendarUser getExistedByUserIdAndCalendarId(int userId, int calendarId) {
        return findByUserIdAndCalendarId(userId, calendarId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "User with userId: " + userId + " not found in calendar with id: " + calendarId));
    }
}
