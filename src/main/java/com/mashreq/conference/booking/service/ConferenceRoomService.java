package com.mashreq.conference.booking.service;

import com.mashreq.conference.booking.dto.ConferenceRoomRequest;
import com.mashreq.conference.booking.dto.ConferenceRoomResponse;
import com.mashreq.conference.booking.exception.ConferenceRoomException;
import com.mashreq.conference.booking.mapper.ConferenceRoomMapper;
import com.mashreq.conference.booking.repository.ConferenceRoomRepository;
import com.mashreq.conference.booking.repository.RoomMaintenanceRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Slf4j
public class ConferenceRoomService {

    ConferenceRoomRepository repository;
    ConferenceRoomMapper conferenceRoomMapper;
    RoomMaintenanceRepository maintenanceRepository;

    public ConferenceRoomResponse create(ConferenceRoomRequest request) {
        repository.findByName(request.getName())
                .ifPresent(room -> {
                    throw new ConferenceRoomException("Conference Room already present with a name: " + request.getName());
                });
        val entity = conferenceRoomMapper.mapToEntity(request);
        val maintenanceList = maintenanceRepository.findAllById(request.getMaintenanceWindowIds());
        entity.setRoomMaintenanceWindow(maintenanceList);
        return conferenceRoomMapper.mapToDto(repository.save(entity));
    }

    public ConferenceRoomResponse findById(Long id) {
        return repository.findById(id)
                .map(conferenceRoomMapper::mapToDto)
                .orElseThrow(() -> new ConferenceRoomException("Conference room not present for Id : " + id));
    }

    public List<ConferenceRoomResponse> findAvailableByTimeRange(LocalTime startTime, LocalTime endTime) {
        return repository.findAvailableRooms(startTime, endTime)
                .stream()
                .map(conferenceRoomMapper::mapToDto)
                .toList();
    }
}
