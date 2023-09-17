package ru.nfm.calendar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nfm.calendar.model.UserProfile;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

    Optional<UserProfile> getUserProfileById(long id);

    Optional<List<UserProfile>> getUserProfileListByCompanyName(String companyName);
}
