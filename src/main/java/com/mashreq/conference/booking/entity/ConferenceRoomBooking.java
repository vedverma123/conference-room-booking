package com.mashreq.conference.booking.entity;

import com.mashreq.conference.booking.validator.ValidDateTime;
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
    @ValidDateTime
    LocalDateTime startTime;

    @Column(nullable = false)
    @ValidDateTime
    LocalDateTime endTime;

    @Column(nullable = false)
    LocalDateTime createdAt;

    @Column(nullable = false)
    int attendees;

    @Column(nullable = false)
    boolean isRoomReleased;

    @Column(nullable = false)
    boolean isRemoved;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
