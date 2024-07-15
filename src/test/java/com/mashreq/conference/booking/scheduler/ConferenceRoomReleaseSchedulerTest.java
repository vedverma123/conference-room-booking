package com.mashreq.conference.booking.scheduler;

import com.mashreq.conference.booking.entity.ConferenceRoom;
import com.mashreq.conference.booking.entity.ConferenceRoomBooking;
import com.mashreq.conference.booking.repository.ConferenceRoomBookingRepository;
import com.mashreq.conference.booking.repository.ConferenceRoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ConferenceRoomReleaseSchedulerTest {
    @Mock
    private ConferenceRoomRepository roomRepository;

    @Mock
    private ConferenceRoomBookingRepository bookingRepository;

    @InjectMocks
    private ConferenceRoomReleaseScheduler scheduler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @CsvSource({
            "0, 1, true",   // Booking expired within interval
            "3, 1, false"   // Booking not expired within interval
    })
    void testReleaseExpiredBookings(int bookingHoursAgo, int intervalHours, boolean expectRelease) {

        LocalDateTime now = LocalDateTime.now();
        ConferenceRoom room = new ConferenceRoom();
        room.setBooked(true);
        ConferenceRoomBooking booking = new ConferenceRoomBooking();
        booking.setRoom(room);
        booking.setEndTime(now.minusHours(bookingHoursAgo));
        booking.setRoomReleased(false);

        when(bookingRepository.findByIsRoomReleasedFalseAndEndTimeBetween(any(), any()))
                .thenReturn(expectRelease ? List.of(booking) : Collections.emptyList());

        scheduler.releaseExpiredBookings();

        if (expectRelease) {
            verify(roomRepository, times(1)).save(room);
            verify(bookingRepository, times(1)).save(booking);
        } else {
            verify(roomRepository, times(0)).save(any());
            verify(bookingRepository, times(0)).save(any());
        }
    }

    @Test
    void testReleaseExpiredBookings_NoBookings() {
        when(bookingRepository.findByIsRoomReleasedFalseAndEndTimeBetween(any(), any()))
                .thenReturn(Collections.emptyList());

        scheduler.releaseExpiredBookings();

        verify(roomRepository, times(0)).save(any());
        verify(bookingRepository, times(0)).save(any());
    }

    @Test
    void testReleaseExpiredBookings_DataAccessException() {
        when(bookingRepository.findByIsRoomReleasedFalseAndEndTimeBetween(any(), any()))
                .thenThrow(new RuntimeException("Database error"));

        scheduler.releaseExpiredBookings();

        verify(roomRepository, times(0)).save(any());
        verify(bookingRepository, times(0)).save(any());
    }

    @Test
    void testReleaseExpiredBookings_RuntimeException() {
        when(bookingRepository.findByIsRoomReleasedFalseAndEndTimeBetween(any(), any()))
                .thenThrow(new RuntimeException("Unexpected error"));

        scheduler.releaseExpiredBookings();

        verify(roomRepository, times(0)).save(any());
        verify(bookingRepository, times(0)).save(any());
    }
}
