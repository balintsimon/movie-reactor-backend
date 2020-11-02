package com.drbsimon.cinema;

import com.drbsimon.cinema.model.Room;
import com.drbsimon.cinema.model.Seat;
import com.drbsimon.cinema.model.wrapper.SeatListWrapper;
import com.drbsimon.cinema.repository.RoomRepository;
import com.drbsimon.cinema.service.SeatService;
import com.drbsimon.cinema.repository.SeatRepository;
import javax.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@ComponentScan(basePackages = {"com.drbsimon.cinema"})
class SeatTest {

    @Autowired
    private SeatRepository repository;

    @Autowired
    private SeatService manager;

    private List<Seat> setUpSeats = new ArrayList<>();

    @Autowired
    private RoomRepository roomRepository;

    private Room testRoom;
    private Long roomId;

    @Test
    public void testRepositoryLoads() throws Exception {
        assertThat(repository).isNotNull();
    }

    @Test
    public void testManagerLoads() throws Exception {
        assertThat(manager).isNotNull();
    }

    @BeforeEach
    void setUp() {
        Room newTestRoom = Room.builder()
                .name("Test Room")
                .numberOfSeatsPerRow(2)
                .numberOfSeatsPerRow(2)
                .capacity(4)
                .build();
        roomRepository.save(newTestRoom);
        this.testRoom = newTestRoom;
        roomId = newTestRoom.getId();

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
        repository.saveAll(Arrays.asList(seat1, seat2, seat3));
        setUpSeats.addAll(Arrays.asList(seat1, seat2, seat3));
    }

    @Test
    void testSaveOneSeat() {
        Seat seat = Seat.builder()
                .rowNumber(2)
                .seatNumber(2)
                .room(null)
                .build();

        repository.saveAndFlush(seat);
        List<Seat> newSeats = repository.findAll();
        assertThat(newSeats).hasSize(setUpSeats.size() + 1);
    }

    @Test
    void testSaveOneSeatWithInvalidRowNumber() {
        Seat seat = Seat.builder()
                .rowNumber(-1)
                .seatNumber(2)
                .room(null)
                .build();

        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            repository.saveAndFlush(seat);
        });
    }

    @Test
    void testSaveOneSeatWithInvalidSeatNumber() {
        Seat seat = Seat.builder()
                .rowNumber(1)
                .seatNumber(-1)
                .room(null)
                .build();

        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            repository.saveAndFlush(seat);
        });
    }

    @Test
    void testSaveSeveralSeat() {
        Seat seat1 = Seat.builder()
                .rowNumber(2)
                .seatNumber(3)
                .room(null)
                .build();

        Seat seat2 = Seat.builder()
                .rowNumber(1)
                .seatNumber(2)
                .room(null)
                .build();

        Seat seat3 = Seat.builder()
                .rowNumber(2)
                .seatNumber(2)
                .room(null)
                .build();

        List<Seat> newSeats = Arrays.asList(seat1, seat2, seat3);

        repository.saveAll(newSeats);
        List<Seat> newAllSeats = repository.findAll();
        assertThat(newAllSeats).hasSize(setUpSeats.size() + newSeats.size());
    }

    @Test
    void testFindSeatsByRoomIdWithoutSavingNewSeat() {
        SeatListWrapper wrapper = manager.getAllSeatsByRoomId(roomId);
        List<Seat> foundSeats = wrapper.getSeats();
        assertThat(foundSeats).hasSize(setUpSeats.size());
    }

    @Test
    void testFindSeatsByRoomIdWithSavingNewSeatWithoutRoom() {
        Seat seat = Seat.builder()
                .rowNumber(2)
                .seatNumber(2)
                .room(null)
                .build();

        repository.saveAndFlush(seat);

        SeatListWrapper wrapper = manager.getAllSeatsByRoomId(roomId);
        List<Seat> foundSeats = wrapper.getSeats();
        assertThat(foundSeats).hasSize(setUpSeats.size());
    }

    @Test
    void testFindSeatsByRoomIdWithSavingNewSeatWithRoom() {
        Seat seat = Seat.builder()
                .rowNumber(2)
                .seatNumber(2)
                .room(testRoom)
                .build();

        repository.saveAndFlush(seat);

        SeatListWrapper wrapper = manager.getAllSeatsByRoomId(roomId);
        List<Seat> foundSeats = wrapper.getSeats();
        assertThat(foundSeats).hasSize(setUpSeats.size() + 1);
    }

    @Test
    void testFindSeatsByRoomIdWithNewSeats() {
        Seat seatWithRoom = Seat.builder()
                .rowNumber(2)
                .seatNumber(2)
                .room(testRoom)
                .build();

        Seat seatWithoutRoom = Seat.builder()
                .rowNumber(1)
                .seatNumber(1)
                .room(null)
                .build();

        repository.saveAll(Arrays.asList(seatWithRoom, seatWithoutRoom));

        SeatListWrapper wrapper = manager.getAllSeatsByRoomId(roomId);
        List<Seat> foundSeats = wrapper.getSeats();
        assertThat(foundSeats).hasSize(setUpSeats.size() + 1);
    }

    @Test
    void testGetSeatById() {
        Long seatId = 10L;
        Seat seat1 = Seat.builder()
                .rowNumber(2)
                .seatNumber(3)
                .room(null)
                .build();

        Seat seat2 = Seat.builder()
                .id(seatId)
                .rowNumber(2)
                .seatNumber(2)
                .room(null)
                .build();

        List<Seat> newSeats = Arrays.asList(seat1, seat2);

        repository.saveAll(newSeats);
        Seat foundSeat = manager.getSeatById(seatId);
        assertThat(foundSeat).isEqualTo(seat2);
    }

    @Test
    void testSeatIsNotFound() {
        Long seatId = 10L;
        Seat foundSeat = manager.getSeatById(seatId);
        assertThat(foundSeat).isEqualTo(null);
    }

    @Test
    void testFindAllInManager() {
        List<Seat> repoSeats = repository.findAll();
        SeatListWrapper wrapper = manager.getAllSeats();
        List<Seat> managerSeats = wrapper.getSeats();
        assertThat(repoSeats).isEqualTo(managerSeats);
    }
}