package com.mashreq.conference.booking.mapper;

import com.mashreq.conference.booking.dto.ConferenceRoomBookingRequest;
import com.mashreq.conference.booking.dto.ConferenceRoomBookingResponse;
import com.mashreq.conference.booking.entity.ConferenceRoomBooking;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConferenceRoomBookingMapper {

    ConferenceRoomBooking mapToEntity(ConferenceRoomBookingRequest source);

    ConferenceRoomBookingResponse mapToDto(ConferenceRoomBooking source);
}
