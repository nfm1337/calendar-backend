package ru.nfm.calendar.exception;

import java.io.Serial;

public class UserNotFoundException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 112312313L;

    public UserNotFoundException(long userId, String message) {
        super(String.format("Failed for [%o]: %s", userId, message));
    }
}
