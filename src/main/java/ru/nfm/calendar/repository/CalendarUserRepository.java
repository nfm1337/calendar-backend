package ru.nfm.calendar.repository;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.nfm.calendar.dto.CalendarUserDto;
import ru.nfm.calendar.model.CalendarUser;

import java.util.List;
import java.util.Optional;

@Repository
public interface CalendarUserRepository extends JpaRepository<CalendarUser, Integer> {

    @Query("SELECT cu FROM CalendarUser cu WHERE cu.userProfile.id =:userId AND cu.calendar.id = :calendarId")
    Optional<CalendarUser> findCalendarUserByUserIdAndCalendarId(int userId, int calendarId);

    @Query("SELECT cu FROM CalendarUser cu WHERE cu.userProfile.id = :userId AND cu.calendar.id = :calendarId")
    Optional<CalendarUser> findByUserIdAndCalendarId(int userId, int calendarId);

    @Query("SELECT new ru.nfm.calendar.dto.CalendarUserDto(u.id, u.email, up.firstName, up.lastName, cu.calendarRole)" +
            "FROM CalendarUser cu " +
            "JOIN UserProfile up ON up.id = cu.userProfile.id " +
            "JOIN User u ON u.id = up.id " +
            "WHERE cu.calendar.id = :calendarId")
    List<CalendarUserDto> getCalendarUserDtoListByUserIdAndCalendarId(int calendarId);

    boolean existsByUserProfileIdAndCalendarId(int userId, int calendarId);

    default CalendarUser getExistedByUserIdAndCalendarId(int userId, int calendarId) {
        return findByUserIdAndCalendarId(userId, calendarId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "User with userId: " + userId + " not found in calendar with id: " + calendarId));
    }
}
