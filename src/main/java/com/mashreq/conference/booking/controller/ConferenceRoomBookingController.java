package com.mashreq.conference.booking.controller;

import com.mashreq.conference.booking.dto.ConferenceRoomBookingRequest;
import com.mashreq.conference.booking.dto.ConferenceRoomBookingResponse;
import com.mashreq.conference.booking.service.ConferenceRoomBookingService;
import com.mashreq.conference.booking.swagger.CreateConferenceRoomBookingApi;
import com.mashreq.conference.booking.swagger.DeleteConferenceRoomBookingApi;
import com.mashreq.conference.booking.swagger.GetConferenceRoomBookingApi;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/booking")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Validated
public class ConferenceRoomBookingController {

    ConferenceRoomBookingService roomBookingService;
    @PostMapping
    @CreateConferenceRoomBookingApi
    public ResponseEntity<ConferenceRoomBookingResponse> book(@RequestBody @Valid ConferenceRoomBookingRequest request) {
        return ResponseEntity.ok(roomBookingService.create(request));
    }

    @GetMapping("/{id}")
    @GetConferenceRoomBookingApi
    public ResponseEntity<ConferenceRoomBookingResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(roomBookingService.findById(id));
    }

    @DeleteMapping("/{id}")
    @DeleteConferenceRoomBookingApi
    public ResponseEntity<ConferenceRoomBookingResponse> delete(@PathVariable Long id) {
        return ResponseEntity.ok(roomBookingService.delete(id));
    }
}
