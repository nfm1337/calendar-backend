package ru.nfm.calendar.dto;

import ru.nfm.calendar.model.UserProfile;
import ru.nfm.calendar.util.validation.NoHtml;

public record UserProfileDto(
        @NoHtml
        String email,
        @NoHtml
        String firstName,
        @NoHtml
        String lastName,
        @NoHtml
        String surName,
        @NoHtml
        String companyName,
        @NoHtml
        String position
) {

    public UserProfileDto(String email, UserProfile userProfile) {
        this(email, userProfile.getFirstName(), userProfile.getLastName(), userProfile.getSurName(),
                userProfile.getCompanyName(), userProfile.getPosition());
    }
}
