package com.mashreq.conference.booking.mapper;

import com.mashreq.conference.booking.dto.ConferenceRoomRequest;
import com.mashreq.conference.booking.dto.ConferenceRoomResponse;
import com.mashreq.conference.booking.entity.ConferenceRoom;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ConferenceRoomMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "capacity", target = "capacity")
    @Mapping(source = "maintenanceWindow", target = "roomMaintenanceWindow")
    ConferenceRoom mapToEntity(ConferenceRoomRequest source);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "capacity", target = "capacity")
    @Mapping(source = "roomMaintenanceWindow", target = "maintenanceWindow")
    ConferenceRoomResponse mapToDto(ConferenceRoom source);
}
