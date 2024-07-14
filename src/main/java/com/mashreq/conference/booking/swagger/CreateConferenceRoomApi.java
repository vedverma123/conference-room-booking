package com.mashreq.conference.booking.swagger;

import com.mashreq.conference.booking.dto.ConferenceRoomRequest;
import com.mashreq.conference.booking.dto.ConferenceRoomResponse;
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
        summary = "Create a new Conference Room",
        description = "Create a new Conference Room by specifying the request body. "
)
@ApiResponses({
        @ApiResponse(responseCode = "201",
                content = { @Content(schema = @Schema(implementation = ConferenceRoomResponse.class),
                        mediaType = "application/json")}),
        @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema() )}),
        @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema() )})
})
@RequestBody(
        required = true,
        content = @Content(
                schema = @Schema(implementation = ConferenceRoomRequest.class),
                mediaType = MediaType.APPLICATION_JSON_VALUE
        )
)
@Retention(RetentionPolicy.RUNTIME)
public @interface CreateConferenceRoomApi {
}
