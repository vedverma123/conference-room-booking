package com.mashreq.conference.booking.controller;

import com.mashreq.conference.booking.dto.ConferenceRoomBookingRequest;
import com.mashreq.conference.booking.dto.ConferenceRoomBookingResponse;
import com.mashreq.conference.booking.service.ConferenceRoomBookingService;
import com.mashreq.conference.booking.swagger.CreateConferenceRoomBookingApi;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/booking")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ConferenceRoomBookingController {

    ConferenceRoomBookingService roomBookingService;
    @PostMapping
    @CreateConferenceRoomBookingApi
    public ResponseEntity<ConferenceRoomBookingResponse> book(@RequestBody @Valid ConferenceRoomBookingRequest request) {
        return ResponseEntity.ok(roomBookingService.create(request));
    }
}
