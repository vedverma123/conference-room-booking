package com.mashreq.conference.booking.validator;

import com.mashreq.conference.booking.dto.ConferenceRoomBookingRequest;
import jakarta.validation.ValidationException;

public interface RoomBookingValidator {
    void validate(ConferenceRoomBookingRequest bookingRequest) throws ValidationException;
}
