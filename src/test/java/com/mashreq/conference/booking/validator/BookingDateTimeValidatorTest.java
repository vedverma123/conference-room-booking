package com.mashreq.conference.booking.validator;

import com.mashreq.conference.booking.dto.ConferenceRoomBookingRequest;
import jakarta.validation.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BookingDateTimeValidatorTest {
    @InjectMocks
    private BookingDateTimeValidator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(validator, "validIntervalMinutes", 15);
    }

    @ParameterizedTest
    @CsvSource({
            "2024-07-15T10:00, 2024-07-15T10:15, true",   // Valid duration
            "2024-07-10T10:00, 2024-07-10T10:20, false",  // Invalid duration
    })
    void testIsValidDuration(String startTime, String endTime, boolean isValid) {
        LocalDateTime start = LocalDateTime.parse(startTime);
        LocalDateTime end = LocalDateTime.parse(endTime);
        ConferenceRoomBookingRequest request = new ConferenceRoomBookingRequest();
        request.setStartTime(start);
        request.setEndTime(end);

        if (isValid) {
            assertDoesNotThrow(() -> validator.validate(request));
        } else {
            assertThrows(ValidationException.class, () -> validator.validate(request));
        }
    }

    @ParameterizedTest
    @CsvSource({
            "2024-07-20T10:00, 2024-07-20T10:15, false",
            "2024-07-01T08:00, 2024-07-01T08:15, false"
    })
    void testValidateDates(String startTime, String endTime, boolean shouldValidate) {
        LocalDateTime start = LocalDateTime.parse(startTime);
        LocalDateTime end = LocalDateTime.parse(endTime);
        ConferenceRoomBookingRequest request = new ConferenceRoomBookingRequest();
        request.setStartTime(start);
        request.setEndTime(end);

        if (shouldValidate) {
            assertDoesNotThrow(() -> validator.validate(request));
        } else {
            assertThrows(ValidationException.class, () -> validator.validate(request));
        }
    }
}
