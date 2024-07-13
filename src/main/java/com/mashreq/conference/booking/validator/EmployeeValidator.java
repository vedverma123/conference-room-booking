package com.mashreq.conference.booking.validator;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class EmployeeValidator {/*implements RoomBookingValidator {

    EmployeeRepository employeeRepository;
    @Override
    public void validate(ConferenceRoomBookingRequest bookingRequest) throws ValidationException {
        employeeRepository.findById(bookingRequest.getEmployeeId())
                .orElseThrow(() -> new ValidationException("Employee not exist for the given Id, please check again"));
    }*/
}
