package com.drbsimon.booking.repository;

import com.drbsimon.booking.entity.Reservation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository repository;

    List<Reservation> originalReservations;

    @Test
    public void testRepositoryLoads() throws Exception {
        assertThat(repository).isNotNull();
    }

    @BeforeEach
    void init() {
        originalReservations = new ArrayList<>();

        Reservation reservation1 = new Reservation().builder()
                .showId(1L)
                .visitorId(1L)
                .seatId(1L)
                .build();
        repository.save(reservation1);

        Reservation reservation1_1 = new Reservation().builder()
                .showId(1L)
                .visitorId(1L)
                .seatId(2L)
                .build();
        repository.save(reservation1_1);

        Reservation reservation2 = new Reservation().builder()
                .showId(2L)
                .visitorId(2L)
                .seatId(1L)
                .build();
        repository.save(reservation2);

        Reservation reservation2_1 = new Reservation().builder()
                .showId(2L)
                .visitorId(2L)
                .seatId(2L)
                .build();
        repository.save(reservation2_1);

        originalReservations.addAll(Arrays.asList(reservation1, reservation1_1, reservation2, reservation2_1));
    }

    @Test
    public void testSaveOneSimple() {
        Reservation reservation = new Reservation().builder()
                .showId(3L)
                .visitorId(2L)
                .seatId(2L)
                .build();
        repository.save(reservation);

        List<Reservation> newReservations = repository.findAll();
        assertThat(newReservations).hasSize(originalReservations.size() + 1);
    }


}