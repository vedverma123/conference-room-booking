package com.mashreq.conference.booking.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static com.mashreq.conference.booking.constants.AppConstants.DATE_TIME_PATTERN;

@Component
@Slf4j
public class DateTimeValidator implements ConstraintValidator<ValidDateTime, LocalDateTime> {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    @Override
    public boolean isValid(LocalDateTime localDateTime, ConstraintValidatorContext constraintValidatorContext) {
        try {
            formatter.format(localDateTime);
            return true;
        } catch (DateTimeParseException e) {
            log.error("Unable to parse date" + e);
            return false;
        }
    }
}
