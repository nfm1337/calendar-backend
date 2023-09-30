package ru.nfm.calendar.util.validation.timezone;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = TimezoneValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidTimezone {
    String message() default "должно быть валидным";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
