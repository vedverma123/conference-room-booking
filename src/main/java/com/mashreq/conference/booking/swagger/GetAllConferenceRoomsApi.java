package com.mashreq.conference.booking.swagger;

import com.mashreq.conference.booking.dto.ConferenceRoomResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Operation(
        summary = "Returns the list of the conference rooms",
        description = "Returns the list of the conference rooms"
)
@ApiResponses({
        @ApiResponse(responseCode = "200",
                content = { @Content(schema = @Schema(implementation = ConferenceRoomResponse.class),
                        mediaType = "application/json")}),
        @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema() )}),
        @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema() )})
})
@Retention(RetentionPolicy.RUNTIME)
public @interface GetAllConferenceRoomsApi {
}
