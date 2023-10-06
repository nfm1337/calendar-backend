package ru.nfm.calendar.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum CalendarRole {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    CREATOR,
    EDITOR,
    USER,
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    PENDING_INVITE
}