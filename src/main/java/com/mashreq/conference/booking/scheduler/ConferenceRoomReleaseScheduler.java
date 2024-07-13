package com.mashreq.conference.booking.scheduler;

import com.mashreq.conference.booking.repository.ConferenceRoomBookingRepository;
import com.mashreq.conference.booking.repository.ConferenceRoomRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public class ConferenceRoomReleaseScheduler {

    final ConferenceRoomRepository roomRepository;
    final ConferenceRoomBookingRepository bookingRepository;

    @Value("${app.scheduler.roomRelease.timeIntervalHour}")
    int timeInterval;

    @Scheduled(fixedRateString = "${app.scheduler.roomRelease.fixedRate}")
    @Transactional
    public void releaseExpiredBookings() {
        try {
            val now = LocalDateTime.now();
            val expiredBookings = bookingRepository.findByIsRoomReleasedFalseAndEndTimeBetween(now.minusHours(timeInterval), now);
            if(expiredBookings.isEmpty()) {
                log.debug("No booking to release");
            }
            expiredBookings.forEach(booking -> {
                log.debug("Releasing the room for booking id: " + booking.getId());

                booking.setRoomReleased(true);
                val room = booking.getRoom();
                room.setBooked(false);

                roomRepository.save(room);
                bookingRepository.save(booking);
            });
        } catch (DataAccessException dae) {
            log.error("Database error during room release: {}", dae.getMessage(), dae);
            // Optionally, raise a database-related alert here.
        } catch (Exception e) {
            log.error("Unexpected error during room release: {}", e.getMessage(), e);
            // Optionally, raise a general alert here.
        }
    }
}
