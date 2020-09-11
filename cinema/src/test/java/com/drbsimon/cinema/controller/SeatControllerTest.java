package com.drbsimon.cinema.controller;

import com.drbsimon.cinema.entity.Room;
import com.drbsimon.cinema.entity.Seat;
import com.drbsimon.cinema.model.SeatListWrapper;
import com.drbsimon.cinema.repository.SeatManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
class SeatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired

    @MockBean
    private SeatManager service;

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
    void getSeatById() {
    }

    @Test
    void getSeatByRoomId() {
    }
}