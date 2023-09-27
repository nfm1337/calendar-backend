package ru.nfm.calendar.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(long userId, String message) {
        super(String.format("Failed for [%o]: %s", userId, message));
    }
}
