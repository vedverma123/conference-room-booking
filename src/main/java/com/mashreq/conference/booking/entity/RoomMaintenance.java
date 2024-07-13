package com.mashreq.conference.booking.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalTime;

import static lombok.AccessLevel.PRIVATE;

@Entity
@Table(
        name = "room_maintenance"/*,
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"startTime", "endTime"})
        }*/
)
@FieldDefaults(level = PRIVATE)
@Getter
@Setter
public class RoomMaintenance {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    Long id;

    @Column(nullable = false)
    LocalTime startTime;

    @Column(nullable = false)
    LocalTime endTime;

}
