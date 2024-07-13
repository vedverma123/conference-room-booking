package com.mashreq.conference.booking.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Entity
@Table(name = "conference_room_booking")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class ConferenceRoomBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    Long id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    ConferenceRoom room;

    @Column(nullable = false)
    LocalDateTime startTime;

    @Column(nullable = false)
    LocalDateTime endTime;

    @Column(nullable = false)
    LocalDateTime createdAt;

    @Column(nullable = false)
    int attendees;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
