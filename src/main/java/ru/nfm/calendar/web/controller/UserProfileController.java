package ru.nfm.calendar.web.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.nfm.calendar.model.User;
import ru.nfm.calendar.model.UserProfile;
import ru.nfm.calendar.payload.request.UserProfileRequest;
import ru.nfm.calendar.payload.response.UserProfileSetupResponse;
import ru.nfm.calendar.payload.response.UserProfileUpdateResponse;
import ru.nfm.calendar.service.UserProfileService;

@RestController
@RequestMapping(value = UserProfileController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class UserProfileController {
    static final String REST_URL = "/profiles";
    private final UserProfileService userProfileService;

    @PostMapping
    public ResponseEntity<UserProfileSetupResponse> createUserProfile(@AuthenticationPrincipal User user,
                                                                      @Valid @RequestBody UserProfileRequest request) {
        UserProfile userProfile = userProfileService.setupUserProfile(user, request);
        var response = new UserProfileSetupResponse(
                "Регистрация завершена успешно",
                userProfile.getId(),
                userProfile.getUser().getEmail());

        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<UserProfileUpdateResponse> updateUserProfile(@AuthenticationPrincipal User user,
                                                                       @Valid @RequestBody UserProfileRequest request) {
        var response = new UserProfileUpdateResponse(
                "Обновление профиля завершено успешно",
                userProfileService.updateUserProfile(user, request));

        return ResponseEntity.ok(response);
    }
}
