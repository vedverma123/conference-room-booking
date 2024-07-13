package com.mashreq.conference.booking.validator;

import com.mashreq.conference.booking.dto.ConferenceRoomBookingRequest;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class BookingDateTimeValidator implements RoomBookingValidator {

    private static final int VALID_INTERVAL_MIN = 15;
    @Override
    public void validate(ConferenceRoomBookingRequest bookingRequest) throws ValidationException {
        if(bookingRequest.getStartTime().toLocalDate().isAfter(LocalDate.now())) {
            throw new ValidationException("Room can not be booked for the future date");
        }
        validateBookingTime(bookingRequest.getStartTime(), bookingRequest.getEndTime());
    }

    private void validateBookingTime(LocalDateTime startTime, LocalDateTime endTime) {
        if(!isValidInterval(startTime) || !isValidInterval(endTime)) {
            throw new ValidationException("Booking times must be in 15-minute intervals.");
        }

        if(!isValidDuration(startTime, endTime)) {
            throw new ValidationException("Booking duration must be in multiples of 15 minutes.");
        }
    }

    private boolean isValidInterval(LocalDateTime time) {
        return time.getMinute() % VALID_INTERVAL_MIN == 0;
    }

    private boolean isValidDuration(LocalDateTime start, LocalDateTime end) {
        return Duration.between(start, end).toMinutes() % VALID_INTERVAL_MIN == 0;
    }
}
