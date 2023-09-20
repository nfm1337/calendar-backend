package ru.nfm.calendar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.nfm.calendar.model.UserProfile;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    Optional<UserProfile> findById(int id);

    @Query("SELECT up FROM UserProfile up JOIN up.user u WHERE u.email = :email")
    Optional<UserProfile> findByEmail(String email);

    default UserProfile getExistedByEmail(String email) {
        return findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User with "));
    }
}
