package ru.nfm.calendar.service;

import ru.nfm.calendar.dto.UserProfileDto;

public interface UserProfileService {

    UserProfileDto setupUserProfile(int userId, UserProfileDto userProfileDto);

    UserProfileDto updateUserProfile(int userId, UserProfileDto userProfileDto);
}
