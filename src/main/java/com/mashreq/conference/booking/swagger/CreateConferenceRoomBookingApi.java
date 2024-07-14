package com.mashreq.conference.booking.swagger;

import com.mashreq.conference.booking.dto.ConferenceRoomBookingRequest;
import com.mashreq.conference.booking.dto.ConferenceRoomBookingResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Operation(
        summary = "Create a Conference Room Booking",
        description = "Create a new Conference Room Booking by specifying the request body. "
)
@ApiResponses({
        @ApiResponse(responseCode = "201",
                content = { @Content(schema = @Schema(implementation = ConferenceRoomBookingResponse.class),
                        mediaType = "application/json")}),
        @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema() )}),
        @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema() )})
})
@RequestBody(
        required = true,
        content = @Content(
                schema = @Schema(implementation = ConferenceRoomBookingRequest.class),
                mediaType = MediaType.APPLICATION_JSON_VALUE
        )
)
@Retention(RetentionPolicy.RUNTIME)
public @interface CreateConferenceRoomBookingApi {
}
