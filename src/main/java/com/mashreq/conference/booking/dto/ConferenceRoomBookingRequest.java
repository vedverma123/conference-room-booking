package com.mashreq.conference.booking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

import static com.mashreq.conference.booking.constants.AppConstants.DATE_TIME_PATTERN;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConferenceRoomBookingRequest {

    @NotNull(message = "Start Time cant be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
    @Schema(type = "string", pattern = DATE_TIME_PATTERN, example = "10-10-2023 09:00")
    LocalDateTime startTime;

    @NotNull(message = "End Time cant be null")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
    @Schema(type = "string", pattern = DATE_TIME_PATTERN, example = "10-10-2023 09:15")
    LocalDateTime endTime;

    @Min(value = 1, message = "Attendees have to be min 1")
    int attendees;

/*
    @NotNull(message = "Employee id can not be null")
    long employeeId;
*/
}
