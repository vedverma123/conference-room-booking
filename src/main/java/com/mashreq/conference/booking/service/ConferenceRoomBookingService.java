package com.mashreq.conference.booking.service;

import com.mashreq.conference.booking.dto.ConferenceRoomBookingRequest;
import com.mashreq.conference.booking.dto.ConferenceRoomBookingResponse;
import com.mashreq.conference.booking.entity.ConferenceRoom;
import com.mashreq.conference.booking.exception.ConferenceRoomException;
import com.mashreq.conference.booking.mapper.ConferenceRoomBookingMapper;
import com.mashreq.conference.booking.repository.ConferenceRoomBookingRepository;
import com.mashreq.conference.booking.repository.ConferenceRoomRepository;
import com.mashreq.conference.booking.validator.BookingValidatorChain;
import jakarta.persistence.OptimisticLockException;
import jakarta.validation.ValidationException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public class ConferenceRoomBookingService {

    ConferenceRoomBookingRepository bookingRepository;
    ConferenceRoomRepository roomRepository;
    ConferenceRoomBookingMapper bookingMapper;
    BookingValidatorChain bookingValidatorChain;

    @Transactional
    public ConferenceRoomBookingResponse create(ConferenceRoomBookingRequest bookingRequest) {
        bookingValidatorChain.validate(bookingRequest);
        val attendees = bookingRequest.getAttendees();
        val availableRooms = roomRepository.findByCapacityGreaterThanEqualAndIsBookedFalse(attendees);
        if(availableRooms.isEmpty()) {
            throw new ValidationException("No suitable conference room found for " + attendees + " attendees.");
        }
        for(ConferenceRoom eligibleRoom : availableRooms) {
            try{
                eligibleRoom.setBooked(true);
                roomRepository.saveAndFlush(eligibleRoom);
                val conferenceRoomBooking = bookingMapper.mapToEntity(bookingRequest);
                conferenceRoomBooking.setRoom(eligibleRoom);
                return bookingMapper.mapToDto(bookingRepository.save(conferenceRoomBooking));
            } catch (OptimisticLockException exception) {
                log.info("Another user is also trying to book a room, continue to the next one");
            }
        }
        throw new ConferenceRoomException("No suitable conference room found for : " + attendees);
    }
}
