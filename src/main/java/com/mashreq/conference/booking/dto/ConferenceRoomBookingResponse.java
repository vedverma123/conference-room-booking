package com.mashreq.conference.booking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

import static com.mashreq.conference.booking.constants.AppConstants.DATE_TIME_PATTERN;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class ConferenceRoomBookingResponse {

    Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
    LocalDateTime startTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
    LocalDateTime endTime;
    int attendees;
    String roomName;
    boolean isRemoved;
}
