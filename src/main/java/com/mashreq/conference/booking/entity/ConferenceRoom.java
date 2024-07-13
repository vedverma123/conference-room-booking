package com.mashreq.conference.booking.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(
        name = "conference_room",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"name"})
        }
)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class ConferenceRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    Long id;

    @Column(nullable = false)
    String name;

    //constraint here for where clause.
    @Column(nullable = false)
    int capacity;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    List<RoomMaintenance> roomMaintenanceWindow;

    @Column(nullable = false)
    boolean isBooked;

    @Column(nullable = false, updatable = false)
    LocalDateTime createdAt;

    @Column/*(nullable = false)*/
    LocalDateTime updatedAt;

    @Version
    @Column(nullable = false)
    Long version;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
