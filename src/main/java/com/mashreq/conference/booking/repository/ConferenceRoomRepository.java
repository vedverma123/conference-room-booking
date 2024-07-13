package com.mashreq.conference.booking.repository;

import com.mashreq.conference.booking.entity.ConferenceRoom;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConferenceRoomRepository extends JpaRepository<ConferenceRoom, Long> {
    Optional<ConferenceRoom> findByName(String name);

    @Lock(value = LockModeType.OPTIMISTIC)
    List<ConferenceRoom> findByCapacityGreaterThanEqualAndIsBookedFalse(int attendees);
}
