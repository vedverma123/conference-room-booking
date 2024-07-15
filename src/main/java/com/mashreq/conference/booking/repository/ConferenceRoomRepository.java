package com.mashreq.conference.booking.repository;

import com.mashreq.conference.booking.entity.ConferenceRoom;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ConferenceRoomRepository extends JpaRepository<ConferenceRoom, Long> {
    Optional<ConferenceRoom> findByName(String name);

    @Lock(value = LockModeType.OPTIMISTIC)
    List<ConferenceRoom> findByCapacityGreaterThanEqualAndIsBookedFalse(int attendees);

    @Query("SELECT cr FROM ConferenceRoom cr WHERE cr.isBooked = false AND cr.id NOT IN " +
            "(SELECT cr.id FROM ConferenceRoom cr JOIN cr.roomMaintenanceWindow rm " +
            "WHERE :startTime < rm.endTime AND :endTime > rm.startTime)")
    List<ConferenceRoom> findAvailableRooms(@Param("startTime") LocalTime startTime,
                                            @Param("endTime") LocalTime endTime);

}
