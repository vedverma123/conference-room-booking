package com.mashreq.conference.booking.validator;

import com.mashreq.conference.booking.dto.ConferenceRoomBookingRequest;
import jakarta.validation.ValidationException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookingDateTimeValidator implements RoomBookingValidator {

    @Value("${app.booking.intervalMinutes}")
    int validIntervalMinutes;

    @Override
    public void validate(ConferenceRoomBookingRequest bookingRequest) throws ValidationException {
        if(bookingRequest.getStartTime().toLocalDate().isAfter(LocalDate.now())) {
            throw new ValidationException("Room can not be booked for the future date");
        }
        if(bookingRequest.getStartTime().isBefore(LocalDateTime.now())) {
            throw new ValidationException("Room can not be booked for the past date or time");
        }
        if(!isValidDuration(bookingRequest.getStartTime(), bookingRequest.getEndTime())) {
            throw new ValidationException("Booking duration must be in multiples of 15 minutes.");
        }
    }

    private boolean isValidDuration(LocalDateTime start, LocalDateTime end) {
        return Duration.between(start, end).toMinutes() % validIntervalMinutes == 0;
    }
}
