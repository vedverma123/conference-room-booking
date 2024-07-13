package com.mashreq.conference.booking.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class ConferenceRoomRequest {

    @NotEmpty(message = "Name can't be empty")
    @NotBlank(message = "Name can't be empty")
    @NotNull(message = "Name can't be empty")
    @Valid
    String name;

    @Min(value = 1, message = "Capacity has to be greater than 1")
    int capacity;

    @NotNull
    List<@Valid MaintenanceWindow> maintenanceWindow;
}
