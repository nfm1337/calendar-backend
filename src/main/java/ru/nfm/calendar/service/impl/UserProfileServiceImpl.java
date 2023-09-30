package ru.nfm.calendar.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nfm.calendar.dto.UserProfileDto;
import ru.nfm.calendar.mapper.UserProfileMapper;
import ru.nfm.calendar.model.User;
import ru.nfm.calendar.model.UserProfile;
import ru.nfm.calendar.repository.UserProfileRepository;
import ru.nfm.calendar.repository.UserRepository;
import ru.nfm.calendar.service.UserProfileService;

@Service
@AllArgsConstructor
@Slf4j
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserProfileMapper userProfileMapper;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserProfileDto setupUserProfile(int userId, UserProfileDto userProfileDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find user with id: " + userId));
        UserProfile userProfile = new UserProfile();
        userProfile.setUser(user);
        userProfileMapper.updateUserProfileFromDto(userProfileDto, userProfile);
        return userProfileMapper.toDto(userProfileRepository.save(userProfile));
    }

    @Override
    @Transactional
    public UserProfileDto updateUserProfile(int userId, UserProfileDto userProfileDto) {
        User user = userRepository.findByIdWithUserProfile(userId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find user with id: " + userId));
        UserProfile userProfile = user.getUserProfile();
        userProfileMapper.updateUserProfileFromDto(userProfileDto, userProfile);
        return userProfileMapper.toDto(userProfileRepository.save(userProfile));
    }
}
