package com.drbsimon.cinema.controller;

import com.drbsimon.cinema.entity.Room;
import com.drbsimon.cinema.model.RoomListWrapper;
import com.drbsimon.cinema.repository.RoomManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(controllers = RoomController.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoomManager service;

    private List<Room> rooms;
    private RoomListWrapper roomListWrapper;


    @BeforeEach
    void setUp() {
        this.rooms = new ArrayList<>();
        this.rooms.add(new Room(1L, "1", 1, 1, 1, null));
        this.rooms.add(new Room(2L, "2", 2, 2, 4, null));
        this.rooms.add(new Room(3L, "3", 3, 3, 9, null));

        this.roomListWrapper = new RoomListWrapper();
        roomListWrapper.setRooms(rooms);
    }

    @Test
    void testGetAllRooms() throws Exception {
        given(service.getAllRooms()).willReturn(roomListWrapper);

        this.mockMvc.perform(get("/room"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.rooms.size()", is(rooms.size())));
    }

    @Test
    void testGetRoom() throws Exception {
        final long roomId = 4L;
        final String name = "tested";
        final int numberOfRows = 1;
        final int numberOfSeatsInRow = 2;
        final int capacity = 2;
        final Room room = new Room(roomId, name, numberOfRows, numberOfSeatsInRow, capacity, null);

        given(service.getRoomById(roomId)).willReturn(room);

        this.mockMvc.perform(get("/room/" + roomId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is((int) roomId)))
                .andExpect(jsonPath("$.numberOfRows", is(numberOfRows)))
                .andExpect(jsonPath("$.numberOfSeatsPerRow", is(numberOfSeatsInRow)))
                .andExpect(jsonPath("$.capacity", is(capacity)));
    }
}