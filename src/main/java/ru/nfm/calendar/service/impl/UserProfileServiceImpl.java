package ru.nfm.calendar.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nfm.calendar.dto.UserProfileDto;
import ru.nfm.calendar.mapper.UserProfileMapper;
import ru.nfm.calendar.model.User;
import ru.nfm.calendar.model.UserProfile;
import ru.nfm.calendar.payload.request.UserProfileRequest;
import ru.nfm.calendar.repository.UserProfileRepository;
import ru.nfm.calendar.service.UserProfileService;

@Service
@AllArgsConstructor
@Slf4j
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserProfileMapper userProfileMapper;

    @Override
    @Transactional
    public UserProfile setupUserProfile(User user, UserProfileRequest request) {
        UserProfile userProfile = new UserProfile();
        userProfile.setUser(user);
        userProfile.setFirstName(request.firstName());
        userProfile.setLastName(request.lastName());
        userProfile.setSurName(request.surName());
        userProfile.setCompanyName(request.companyName());
        userProfile.setPosition(request.position());
        userProfile.setTimezone(request.timezone());

        return userProfileRepository.save(userProfile);
    }

    @Override
    @Transactional
    public UserProfileDto updateUserProfile(User user, UserProfileRequest request) {
        UserProfile userProfile = user.getUserProfile();
        userProfile.setFirstName(request.firstName());
        userProfile.setLastName(request.lastName());
        userProfile.setSurName(request.surName());
        userProfile.setCompanyName(request.companyName());
        userProfile.setPosition(request.position());
        userProfile.setTimezone(request.timezone());

        return userProfileMapper.toDto(userProfileRepository.save(userProfile));
    }
}
