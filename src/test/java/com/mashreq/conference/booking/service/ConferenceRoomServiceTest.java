package com.mashreq.conference.booking.service;

import com.mashreq.conference.booking.dto.ConferenceRoomRequest;
import com.mashreq.conference.booking.dto.ConferenceRoomResponse;
import com.mashreq.conference.booking.entity.ConferenceRoom;
import com.mashreq.conference.booking.exception.ConferenceRoomException;
import com.mashreq.conference.booking.mapper.ConferenceRoomMapper;
import com.mashreq.conference.booking.repository.ConferenceRoomRepository;
import com.mashreq.conference.booking.repository.RoomMaintenanceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

class ConferenceRoomServiceTest {
    @Mock
    private ConferenceRoomRepository repository;

    @Mock
    private ConferenceRoomMapper conferenceRoomMapper;

    @Mock
    private RoomMaintenanceRepository maintenanceRepository;

    @InjectMocks
    private ConferenceRoomService conferenceRoomService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateConferenceRoom_Success() {
        ConferenceRoomRequest request = new ConferenceRoomRequest();
        String name = "Amaze";
        request.setName(name);
        ConferenceRoom conferenceRoom = new ConferenceRoom();
        when(repository.findByName(name)).thenReturn(Optional.empty());
        when(conferenceRoomMapper.mapToEntity(request)).thenReturn(conferenceRoom);
        when(maintenanceRepository.findAllById(anyList())).thenReturn(Collections.emptyList());
        when(repository.save(any())).thenReturn(conferenceRoom);
        when(conferenceRoomMapper.mapToDto(any())).thenReturn(new ConferenceRoomResponse());

        ConferenceRoomResponse response = conferenceRoomService.create(request);

        assertNotNull(response);
        verify(repository, times(1)).save(conferenceRoom);
    }

    @Test
    void testCreateConferenceRoom_AlreadyExists() {
        ConferenceRoomRequest request = new ConferenceRoomRequest();
        String name = "Amaze";
        request.setName(name);
        when(repository.findByName(name)).thenReturn(Optional.of(new ConferenceRoom()));

        ConferenceRoomException exception = assertThrows(ConferenceRoomException.class,
                () -> conferenceRoomService.create(request));

        assertEquals("Conference Room already present with a name: " + name, exception.getMessage());
    }

    @Test
    void testFindById_Success() {
        Long id = 1L;
        ConferenceRoom room = new ConferenceRoom();
        when(repository.findById(id)).thenReturn(Optional.of(room));
        when(conferenceRoomMapper.mapToDto(room)).thenReturn(new ConferenceRoomResponse());

        ConferenceRoomResponse response = conferenceRoomService.findById(id);

        assertNotNull(response);
        verify(repository, times(1)).findById(id);
    }

    @Test
    void testFindById_NotFound() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        ConferenceRoomException exception = assertThrows(ConferenceRoomException.class, () -> {
            conferenceRoomService.findById(id);
        });

        assertEquals("Conference room not present for Id : 1", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({
            "10:00, 10:30",   // Valid time range
            "14:00, 14:30"    // Another valid time range
    })
    void testFindAvailableByTimeRange(String startTime, String endTime) {
        LocalTime start = LocalTime.parse(startTime);
        LocalTime end = LocalTime.parse(endTime);
        when(repository.findAvailableRooms(start, end)).thenReturn(Collections.singletonList(new ConferenceRoom()));
        when(conferenceRoomMapper.mapToDto(any())).thenReturn(new ConferenceRoomResponse());

        List<ConferenceRoomResponse> response = conferenceRoomService.findAvailableByTimeRange(start, end);

        assertNotNull(response);
        assertEquals(1, response.size());
        verify(repository, times(1)).findAvailableRooms(start, end);
    }
}
