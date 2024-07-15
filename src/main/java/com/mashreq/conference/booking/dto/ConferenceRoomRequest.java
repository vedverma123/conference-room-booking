package com.mashreq.conference.booking.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ConferenceRoomRequest {

    @NotBlank(message = "Name can't be empty")
    String name;

    @Min(value = 1, message = "Capacity has to be greater than 1")
    int capacity;

    @NotEmpty
    List<Long> maintenanceWindowIds;
}
