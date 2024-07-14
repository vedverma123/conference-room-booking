package com.mashreq.conference.booking.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.val;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(
        name = "conference_room",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name"})
        },
        indexes = {
                @Index(name = "idx_conference_room_name", columnList = "name")
        }
)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class ConferenceRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    int capacity;

    @ManyToMany(fetch = FetchType.EAGER,
                cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "conference_room_maintenance",
            joinColumns = @JoinColumn(name = "conference_room_id"),
            inverseJoinColumns = @JoinColumn(name = "room_maintenance_id")
    )
    List<RoomMaintenance> roomMaintenanceWindow;

    @Column(nullable = false)
    boolean isBooked;

    @Column(nullable = false, updatable = false)
    LocalDateTime createdAt;

    @Column(nullable = false)
    LocalDateTime updatedAt;

    @Version
    @Column(nullable = false)
    Long version;

    @PrePersist
    protected void onCreate() {
        val now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
