package com.mashreq.conference.booking.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(
        name = "room_maintenance",
        indexes = {
                @Index(name = "idx_maintenance_start_end_time", columnList = "startTime, endTime")
        }
)
@FieldDefaults(level = PRIVATE)
@Getter
@Setter
public class RoomMaintenance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @Column(nullable = false)
    LocalTime startTime;

    @Column(nullable = false)
    LocalTime endTime;

    @ManyToMany(mappedBy = "roomMaintenanceWindow")
    List<ConferenceRoom> conferenceRooms;
}
