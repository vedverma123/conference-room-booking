package com.mashreq.conference.booking.repository;

import com.mashreq.conference.booking.entity.ConferenceRoomBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConferenceRoomBookingRepository extends JpaRepository<ConferenceRoomBooking, Long> {
}
