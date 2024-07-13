package com.mashreq.conference.booking.mapper;

import com.mashreq.conference.booking.dto.ConferenceRoomBookingRequest;
import com.mashreq.conference.booking.dto.ConferenceRoomBookingResponse;
import com.mashreq.conference.booking.entity.ConferenceRoomBooking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ConferenceRoomBookingMapper {

    ConferenceRoomBooking mapToEntity(ConferenceRoomBookingRequest source);

    @Mapping(target = "roomName", source = "room.name")
    ConferenceRoomBookingResponse mapToDto(ConferenceRoomBooking source);
}
