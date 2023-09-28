package ru.nfm.calendar.service;

import ru.nfm.calendar.dto.UserProfileDto;
import ru.nfm.calendar.model.User;
import ru.nfm.calendar.model.UserProfile;
import ru.nfm.calendar.payload.request.UserProfileRequest;

public interface UserProfileService {

    UserProfile setupUserProfile(User user, UserProfileRequest request);

    UserProfileDto updateUserProfile(User user, UserProfileRequest request);
}
