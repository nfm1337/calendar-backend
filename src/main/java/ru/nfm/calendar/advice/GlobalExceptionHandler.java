package ru.nfm.calendar.advice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import ru.nfm.calendar.exception.EmailAlreadyExistsException;
import ru.nfm.calendar.exception.TokenRefreshException;

import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = TokenRefreshException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorMessage> handleTokenRefreshException(TokenRefreshException ex, WebRequest request) {
        return createErrorResponse(HttpStatus.FORBIDDEN, ex, request);
    }

    @ExceptionHandler(value = EmailAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> handleEmailAlreadyExistsException(EmailAlreadyExistsException ex, WebRequest request) {
        return createErrorResponse(HttpStatus.BAD_REQUEST, ex, request);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorMessage> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        return createErrorResponse(HttpStatus.FORBIDDEN, ex, request);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        Map<String, String> errors = ex.getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
        return createErrorResponse(HttpStatus.BAD_REQUEST, errors, request);
    }

    private ResponseEntity<ErrorMessage> createErrorResponse(HttpStatus status, Object error, WebRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(
                status.value(),
                new Date(),
                error,
                request.getDescription(false)
        );
        log.error(errorMessage.toString());
        return ResponseEntity.status(status).body(errorMessage);
    }
}
