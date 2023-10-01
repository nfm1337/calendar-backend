package ru.nfm.calendar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nfm.calendar.model.UserProfile;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {

    @Query("SELECT up FROM UserProfile up LEFT JOIN FETCH up.calendarUserList cu LEFT JOIN FETCH cu.calendar WHERE up.id = :id")
    Optional<UserProfile> findByIdWithCalendarUsers(int id);

    @Query("SELECT up.timezone FROM UserProfile up WHERE up.id = :userId")
    String getUserTimezoneByUserId(int userId);

    @Query("SELECT up FROM UserProfile up WHERE up.user.email = :email")
    Optional<UserProfile> findByEmail(String email);
}
