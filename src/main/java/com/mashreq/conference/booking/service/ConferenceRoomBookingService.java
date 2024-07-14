package com.mashreq.conference.booking.service;

import com.mashreq.conference.booking.dto.ConferenceRoomBookingRequest;
import com.mashreq.conference.booking.dto.ConferenceRoomBookingResponse;
import com.mashreq.conference.booking.entity.ConferenceRoom;
import com.mashreq.conference.booking.entity.RoomMaintenance;
import com.mashreq.conference.booking.exception.ConferenceRoomException;
import com.mashreq.conference.booking.mapper.ConferenceRoomBookingMapper;
import com.mashreq.conference.booking.repository.ConferenceRoomBookingRepository;
import com.mashreq.conference.booking.repository.ConferenceRoomRepository;
import com.mashreq.conference.booking.validator.BookingValidatorChain;
import jakarta.persistence.OptimisticLockException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;


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
        if (availableRooms.isEmpty()) {
            throw new ConferenceRoomException("No suitable conference room found for " + attendees + " attendees.");
        }
        val endTime = bookingRequest.getEndTime().toLocalTime();
        val startTime = bookingRequest.getStartTime().toLocalTime();

        for (ConferenceRoom eligibleRoom : availableRooms) {
            try {
                if (!isBookingTimeOverlapsMaintenance(eligibleRoom, startTime, endTime)) {
                    eligibleRoom.setBooked(true);
                    roomRepository.saveAndFlush(eligibleRoom);
                    val conferenceRoomBooking = bookingMapper.mapToEntity(bookingRequest);
                    conferenceRoomBooking.setRoom(eligibleRoom);
                    return bookingMapper.mapToDto(bookingRepository.save(conferenceRoomBooking));
                }
            } catch (OptimisticLockException exception) {
                log.info("Concurrent booking attempt detected. Trying next available room.");
            }
        }
        throw new ConferenceRoomException("Unable to find a suitable conference room for " + attendees + " attendees. " +
                "Conference rooms may be on maintenance");
    }

    public ConferenceRoomBookingResponse findById(Long id) {
        return bookingRepository.findById(id)
                .map(bookingMapper::mapToDto)
                .orElseThrow(() -> new ConferenceRoomException("Unable to find booking for id : " + id));
    }

    @Transactional
    public ConferenceRoomBookingResponse delete(Long id) {
        return bookingRepository.findById(id)
                .map(conferenceRoomBooking -> {
                    log.debug("Soft deleting the booking having id: {}", conferenceRoomBooking.getId());
                    conferenceRoomBooking.getRoom().setBooked(false);
                    roomRepository.save(conferenceRoomBooking.getRoom());
                    conferenceRoomBooking.setRemoved(true);
                    conferenceRoomBooking.setRoomReleased(true);
                    return bookingMapper.mapToDto(bookingRepository.save(conferenceRoomBooking));
                })
                .orElseThrow(() -> new ConferenceRoomException("Unable to find a booking for id: " + id));
    }

    private boolean isBookingTimeOverlapsMaintenance(ConferenceRoom eligibleRoom, LocalTime start, LocalTime end) {
        return eligibleRoom.getRoomMaintenanceWindow()
                .stream()
                .anyMatch(roomMaintenance -> isOverlapping(roomMaintenance, start, end));
    }

    private boolean isOverlapping(RoomMaintenance roomMaintenance, LocalTime start, LocalTime end) {
        return roomMaintenance.getStartTime().isBefore(end) && roomMaintenance.getEndTime().isAfter(start);
    }
}
