package ru.nfm.calendar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nfm.calendar.dto.UserProfileDto;
import ru.nfm.calendar.model.UserProfile;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    @Query("SELECT up FROM UserProfile up JOIN FETCH User u WHERE u.id = :id")
    Optional<UserProfile> findById(int id);

    @Query("SELECT up FROM UserProfile up JOIN FETCH User u WHERE u.email = :email")
    Optional<UserProfile> findByEmail(String email);

    @Query("SELECT NEW ru.nfm.calendar.dto.UserProfileDto(u.email, up) FROM UserProfile up JOIN User u WHERE u.email = :email")
    Optional<UserProfileDto> findByEmailWithUserProfile(String email);

    default UserProfile getExistedByEmail(String email) {
        return findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User with email: " + email + " not found"));
    }
}
