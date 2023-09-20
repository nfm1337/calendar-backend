package ru.nfm.calendar.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nfm.calendar.dto.UserProfileDto;
import ru.nfm.calendar.mapper.UserProfileMapper;
import ru.nfm.calendar.model.User;
import ru.nfm.calendar.model.UserProfile;
import ru.nfm.calendar.payload.request.UserProfileRequest;
import ru.nfm.calendar.repository.UserProfileRepository;
import ru.nfm.calendar.repository.UserRepository;
import ru.nfm.calendar.service.UserProfileService;
import ru.nfm.calendar.util.SecurityUtil;

import static ru.nfm.calendar.util.SecurityUtil.getUserDetails;

@Service
@AllArgsConstructor
@Slf4j
public class UserProfileServiceImpl implements UserProfileService {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserProfileMapper userProfileMapper;

    @Override
    @Transactional
    public UserProfile setupUserProfile(UserProfileRequest request) {
        UserDetails userDetails = getUserDetails();
        String email = userDetails.getUsername();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email: " + email + " not found"));

        return userProfileRepository.save(
                UserProfile.builder()
                        .id(user.getId())
                        .user(user)
                        .firstName(request.firstName())
                        .lastName(request.lastName())
                        .surName(request.surName())
                        .companyName(request.companyName())
                        .position(request.position())
                        .timezone(request.timezone())
                        .build()
        );
    }

    @Override
    @Transactional
    public UserProfileDto updateUserProfile(UserProfileRequest request) {
        var email = SecurityUtil.getUserDetails().getUsername();
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email: " + email + " not found"));

        UserProfile userProfile = user.getUserProfile();
        userProfile.setFirstName(request.firstName());
        userProfile.setLastName(request.lastName());
        userProfile.setSurName(request.surName());
        userProfile.setCompanyName(request.companyName());
        userProfile.setPosition(request.position());
        userProfile.setTimezone(request.timezone());

        userProfile = userProfileRepository.save(userProfile);

        return userProfileMapper.toDto(userProfile);
    }

    @Override
    public UserProfileDto getUserProfileWithEmail(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("UserProfile with id: " + id + " not found"));
        return userProfileMapper.toDto(user.getUserProfile());
    }


}
