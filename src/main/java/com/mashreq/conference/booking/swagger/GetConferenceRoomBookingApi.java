package com.mashreq.conference.booking.swagger;

import com.mashreq.conference.booking.dto.ConferenceRoomBookingResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Operation(
        summary = "Returns the details of the conference room booking",
        description = "Returns the details of the conference room booking"
)
@ApiResponses({
        @ApiResponse(responseCode = "200",
                content = { @Content(schema = @Schema(implementation = ConferenceRoomBookingResponse.class),
                        mediaType = "application/json")}),
        @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema() )}),
        @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema() )})
})
@Retention(RetentionPolicy.RUNTIME)
public @interface GetConferenceRoomBookingApi {
}
