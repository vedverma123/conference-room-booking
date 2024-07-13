package com.mashreq.conference.booking.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class ConferenceRoomBookingResponse {

    Long id;
    LocalDateTime startTime;
    LocalDateTime endTime;
    int attendees;
    int capacity;
    String roomName;
}
