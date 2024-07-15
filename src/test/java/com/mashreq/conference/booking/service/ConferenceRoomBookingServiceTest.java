package com.mashreq.conference.booking.service;

import com.mashreq.conference.booking.dto.ConferenceRoomBookingRequest;
import com.mashreq.conference.booking.dto.ConferenceRoomBookingResponse;
import com.mashreq.conference.booking.entity.ConferenceRoom;
import com.mashreq.conference.booking.entity.ConferenceRoomBooking;
import com.mashreq.conference.booking.entity.RoomMaintenance;
import com.mashreq.conference.booking.exception.ConferenceRoomException;
import com.mashreq.conference.booking.mapper.ConferenceRoomBookingMapper;
import com.mashreq.conference.booking.repository.ConferenceRoomBookingRepository;
import com.mashreq.conference.booking.repository.ConferenceRoomRepository;
import com.mashreq.conference.booking.validator.BookingValidatorChain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ConferenceRoomBookingServiceTest {
    @Mock
    private ConferenceRoomBookingRepository bookingRepository;

    @Mock
    private ConferenceRoomRepository roomRepository;

    @Mock
    private ConferenceRoomBookingMapper bookingMapper;

    @Mock
    private BookingValidatorChain bookingValidatorChain;

    @InjectMocks
    private ConferenceRoomBookingService conferenceRoomBookingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBooking_Success() {
        LocalDateTime now = LocalDateTime.now();
        int attendees = 5;
        ConferenceRoomBookingRequest bookingRequest = new ConferenceRoomBookingRequest();
        bookingRequest.setAttendees(attendees);
        bookingRequest.setStartTime(now);
        bookingRequest.setEndTime(now.plusHours(1));

        ConferenceRoom room = new ConferenceRoom();
        room.setCapacity(10);
        room.setBooked(false);
        room.setRoomMaintenanceWindow(Collections.emptyList());

        ConferenceRoomBooking conferenceRoomBooking = new ConferenceRoomBooking();

        when(roomRepository.findByCapacityGreaterThanEqualAndIsBookedFalse(attendees))
                .thenReturn(Collections.singletonList(room));
        when(bookingMapper.mapToEntity(bookingRequest)).thenReturn(conferenceRoomBooking);
        when(bookingRepository.save(any())).thenReturn(conferenceRoomBooking);
        when(bookingMapper.mapToDto(any())).thenReturn(new ConferenceRoomBookingResponse());

        ConferenceRoomBookingResponse response = conferenceRoomBookingService.create(bookingRequest);

        assertNotNull(response);
        verify(roomRepository, times(1)).saveAndFlush(room);
        verify(bookingRepository, times(1)).save(conferenceRoomBooking);
    }

    @Test
    void testCreateBooking_NoAvailableRoom() {
        ConferenceRoomBookingRequest bookingRequest = new ConferenceRoomBookingRequest();
        int attendees = 10;
        bookingRequest.setAttendees(attendees);

        when(roomRepository.findByCapacityGreaterThanEqualAndIsBookedFalse(attendees))
                .thenReturn(Collections.emptyList());

        ConferenceRoomException exception = assertThrows(ConferenceRoomException.class,
                () -> conferenceRoomBookingService.create(bookingRequest));

        assertEquals("No suitable conference room found for " + attendees + " attendees.",
                exception.getMessage());
    }

    @Test
    void testFindById_Success() {
        Long id = 1L;
        ConferenceRoomBookingResponse booking = new ConferenceRoomBookingResponse();
        ConferenceRoomBooking conferenceRoomBooking = new ConferenceRoomBooking();
        when(bookingRepository.findById(id)).thenReturn(Optional.of(conferenceRoomBooking));
        when(bookingMapper.mapToDto(conferenceRoomBooking)).thenReturn(booking);

        ConferenceRoomBookingResponse response = conferenceRoomBookingService.findById(id);

        assertNotNull(response);
        assertEquals(booking, response);
    }

    @Test
    void testFindById_NotFound() {
        Long id = 1L;
        when(bookingRepository.findById(id)).thenReturn(Optional.empty());

        ConferenceRoomException exception = assertThrows(ConferenceRoomException.class,
                () -> conferenceRoomBookingService.findById(id));

        assertEquals("Unable to find booking for id : 1", exception.getMessage());
    }

    @Test
    void testDelete_Success() {
        Long id = 1L;
        ConferenceRoom room = new ConferenceRoom();
        ConferenceRoomBooking conferenceRoomBooking = new ConferenceRoomBooking();
        conferenceRoomBooking.setId(id);
        conferenceRoomBooking.setRoom(room);
        when(bookingRepository.findById(id)).thenReturn(Optional.of(conferenceRoomBooking));
        when(bookingMapper.mapToDto(conferenceRoomBooking)).thenReturn(new ConferenceRoomBookingResponse());
        when(bookingRepository.save(conferenceRoomBooking)).thenReturn(conferenceRoomBooking);

        ConferenceRoomBookingResponse response = conferenceRoomBookingService.delete(id);

        assertNotNull(response);
        verify(roomRepository, times(1)).save(room);
        verify(bookingRepository, times(1)).save(conferenceRoomBooking);
    }

    @Test
    void testDelete_NotFound() {
        Long id = 1L;
        when(bookingRepository.findById(id)).thenReturn(Optional.empty());

        ConferenceRoomException exception = assertThrows(ConferenceRoomException.class,
                () -> conferenceRoomBookingService.delete(id));

        assertEquals("Unable to find a booking for id: 1", exception.getMessage());
    }

    @Test
    void testFindAll() {
        when(bookingRepository.findAll()).thenReturn(Collections.emptyList());

        List<ConferenceRoomBookingResponse> response = conferenceRoomBookingService.findAll();

        assertNotNull(response);
        assertTrue(response.isEmpty());
    }

    @Test
    void testIsBookingTimeOverlapsMaintenance() {
        ConferenceRoom room = new ConferenceRoom();
        RoomMaintenance maintenance = new RoomMaintenance();
        maintenance.setStartTime(LocalTime.of(10, 0));
        maintenance.setEndTime(LocalTime.of(11, 0));
        room.setRoomMaintenanceWindow(Collections.singletonList(maintenance));

        LocalTime start = LocalTime.of(10, 30);
        LocalTime end = LocalTime.of(11, 30);
        boolean overlaps = conferenceRoomBookingService.isBookingTimeOverlapsMaintenance(room, start, end);

        assertTrue(overlaps);
    }
}
