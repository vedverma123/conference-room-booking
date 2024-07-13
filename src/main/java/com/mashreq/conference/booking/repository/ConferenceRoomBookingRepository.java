package com.mashreq.conference.booking.repository;

import com.mashreq.conference.booking.entity.ConferenceRoomBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ConferenceRoomBookingRepository extends JpaRepository<ConferenceRoomBooking, Long> {

    List<ConferenceRoomBooking> findByIsRoomReleasedFalseAndEndTimeBetween(LocalDateTime start, LocalDateTime end);
}
