package com.mashreq.conference.booking.repository;

import com.mashreq.conference.booking.entity.RoomMaintenance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalTime;
import java.util.Optional;

public interface RoomMaintenanceRepository extends JpaRepository<RoomMaintenance, Long> {
    Optional<RoomMaintenance> findByStartTimeAndEndTime(LocalTime startTime, LocalTime endTime);
}
