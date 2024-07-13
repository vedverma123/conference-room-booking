package com.mashreq.conference.booking.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

import static com.mashreq.conference.booking.constants.AppConstants.DATE_TIME_PATTERN;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class ConferenceRoomBookingRequest {

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
    @Schema(type = "string", pattern = DATE_TIME_PATTERN, example = "10-10-2023 09:00")
    LocalDateTime startTime;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_PATTERN)
    @Schema(type = "string", pattern = DATE_TIME_PATTERN, example = "10-10-2023 09:15")
    LocalDateTime endTime;

    @Min(1)
    int attendees;
}
