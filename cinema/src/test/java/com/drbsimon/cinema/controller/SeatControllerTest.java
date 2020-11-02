package com.drbsimon.cinema.controller;

import com.drbsimon.cinema.model.Room;
import com.drbsimon.cinema.model.Seat;
import com.drbsimon.cinema.model.wrapper.SeatListWrapper;
import com.drbsimon.cinema.service.SeatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = SeatController.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class SeatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SeatService service;

    private List<Seat> seats;
    private SeatListWrapper listWrapper;
    private Room newTestRoom;

    @BeforeEach
    void setUp() {
        seats = new ArrayList<>();
        newTestRoom = Room.builder()
                .id(1L)
                .name("Test Room")
                .numberOfSeatsPerRow(2)
                .numberOfSeatsPerRow(2)
                .capacity(4)
                .build();

        Seat seat1 = Seat.builder()
                .rowNumber(1)
                .seatNumber(1)
                .room(newTestRoom)
                .build();

        Seat seat2 = Seat.builder()
                .rowNumber(1)
                .seatNumber(2)
                .room(newTestRoom)
                .build();

        Seat seat3 = Seat.builder()
                .rowNumber(2)
                .seatNumber(1)
                .room(newTestRoom)
                .build();

        seats.addAll(Arrays.asList(seat1, seat2, seat3));

        listWrapper = new SeatListWrapper();
        listWrapper.setSeats(seats);
    }

    @Test
    void testGetAllSeats() throws Exception {
        given(service.getAllSeats()).willReturn(listWrapper);

        this.mockMvc.perform(get("/seat"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.seats.size()", is(seats.size())));
    }

    @Test
    void testGetSeatById() throws Exception {
        final long seatId = 4L;
        final int rowNumber = 1;
        final int seatNumber = 2;
        Seat newSeat = new Seat(seatId, rowNumber, seatNumber, null);

        given(service.getSeatById(seatId)).willReturn(newSeat);

        this.mockMvc.perform(get("/seat/" + seatId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) seatId)))
                .andExpect(jsonPath("$.rowNumber", is(rowNumber)))
                .andExpect(jsonPath("$.seatNumber", is(seatNumber)));
    }

    @Test
    void testGetSeatByRoomId() throws Exception {
        final long seatId = 4L;
        final int rowNumber = 1;
        final int seatNumber = 2;
        final long roomId = 2L;
        final Room testRoom = new Room(roomId, "tested", 1, 1, 1, null);
        Seat newSeat = new Seat(seatId, rowNumber, seatNumber, testRoom);
        testRoom.setSeats(Arrays.asList(newSeat));

        SeatListWrapper testWrapper = new SeatListWrapper();
        testWrapper.setSeats(Arrays.asList(newSeat));

        given(service.getAllSeatsByRoomId(seatId)).willReturn(testWrapper);

        this.mockMvc.perform(get("/seat/room/" + roomId))
                .andExpect(status().isOk())
                .equals(testWrapper);
    }
}