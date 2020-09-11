package com.drbsimon.cinema;

import com.drbsimon.cinema.entity.Seat;
import com.drbsimon.cinema.repository.SeatManager;
import com.drbsimon.cinema.repository.SeatRepository;
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
    private SeatManager manager;

    private List<Seat> setUpSeats = new ArrayList<>();

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
        Seat seat1 = Seat.builder()
                .rowNumber(1)
                .seatNumber(1)
                .room(null)
                .build();

        Seat seat2 = Seat.builder()
                .rowNumber(1)
                .seatNumber(2)
                .room(null)
                .build();

        Seat seat3 = Seat.builder()
                .rowNumber(2)
                .seatNumber(1)
                .room(null)
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
        repository.saveAll(Arrays.asList(seat1, seat2, seat3));
        List<Seat> newSeats = repository.findAll();
        assertThat(newSeats).hasSize(setUpSeats.size() + 3);
    }
}