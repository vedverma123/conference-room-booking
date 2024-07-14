package com.mashreq.conference.booking.repository;

import com.mashreq.conference.booking.entity.RoomMaintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface RoomMaintenanceRepository extends JpaRepository<RoomMaintenance, Long> {
    Optional<RoomMaintenance> findByStartTimeAndEndTime(LocalTime startTime, LocalTime endTime);
}
