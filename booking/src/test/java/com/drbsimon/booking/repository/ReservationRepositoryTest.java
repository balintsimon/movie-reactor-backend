package com.drbsimon.booking.repository;

import com.drbsimon.booking.entity.Reservation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    @Test
    public void testSaveSeveralSimple() {
        Reservation reservation_1 = new Reservation().builder()
                .showId(3L)
                .visitorId(3L)
                .seatId(3L)
                .build();

        Reservation reservation_2 = new Reservation().builder()
                .showId(4L)
                .visitorId(4L)
                .seatId(4L)
                .build();

        List<Reservation> addedReservations = Arrays.asList(reservation_1, reservation_2);
        repository.saveAll(addedReservations);
        List<Reservation> newReservations = repository.findAll();

        assertThat(newReservations).hasSize(originalReservations.size() + addedReservations.size());
    }

    @Test
    public void testNonUniqueReservationSave() {
        Reservation reservation = new Reservation().builder()
                .showId(3L)
                .visitorId(2L)
                .seatId(2L)
                .build();
        repository.save(reservation);

        Reservation reservation2 = new Reservation().builder()
                .showId(3L)
                .visitorId(2L)
                .seatId(2L)
                .build();
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            repository.saveAndFlush(reservation2);
        });
    }

    @Test
    public void testGetReservationsByVisitorId() {
        Long id = 1L;
        List<Reservation> allReservations = repository.findAll();
        List<Reservation> expected = allReservations.stream()
                .filter(reservation -> reservation.getVisitorId().equals(id))
                .collect(Collectors.toList());

        List<Reservation> actual = repository.getAllByVisitorId(id);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testGetReservationsByNonexistentVisitorId() {
        Long id = 3L;
        List<Reservation> actual = repository.getAllByVisitorId(id);

        assertThat(actual).isEmpty();
    }

    @Test
    public void testGetReservationBySeatIdAndShowId() {
        Long newSeatId = 10L;
        Long newShowId = 10L;
        Reservation reservation = new Reservation().builder()
                .showId(newShowId)
                .visitorId(1L)
                .seatId(newSeatId)
                .build();

        repository.save(reservation);

        Reservation actual = repository.getBySeatIdAndShowId(newSeatId, newShowId);

        assertThat(actual).isEqualTo(reservation);
    }

    @Test
    public void testNoReservationByInvalidSeatIdAndShowId() {

        Long newSeatId = 10L;
        Long newShowId = 10L;
        Reservation reservation = new Reservation().builder()
                .showId(newShowId)
                .visitorId(1L)
                .seatId(newSeatId)
                .build();

        repository.save(reservation);

        Reservation actual = repository.getBySeatIdAndShowId(newSeatId + 1, newShowId);
        Assertions.assertNull(actual);
    }

    @Test
    public void testNoReservationBySeatIdAndInvalidShowId() {

        Long newSeatId = 10L;
        Long newShowId = 10L;
        Reservation reservation = new Reservation().builder()
                .showId(newShowId)
                .visitorId(1L)
                .seatId(newSeatId)
                .build();

        repository.save(reservation);

        Reservation actual = repository.getBySeatIdAndShowId(newSeatId, newShowId + 1);
        Assertions.assertNull(actual);
    }

    @Test
    public void testGetBySeatIdAndVisitorIdAndShowId() {

        Long newSeatId = 10L;
        Long newVisitorId = 10L;
        Long newShowId = 10L;
        Reservation reservation = new Reservation().builder()
                .showId(newShowId)
                .visitorId(newVisitorId)
                .seatId(newSeatId)
                .build();

        repository.save(reservation);

        Reservation actual = repository.getBySeatIdAndVisitorIdAndShowId(newSeatId, newVisitorId, newShowId);
        assertThat(actual).isEqualTo(reservation);
    }

    @Test
    public void testNoByInvalidSeatIdAndVisitorIdAndShowId() {

        Long newSeatId = 10L;
        Long newVisitorId = 10L;
        Long newShowId = 10L;
        Reservation reservation = new Reservation().builder()
                .showId(newShowId)
                .visitorId(newVisitorId)
                .seatId(newSeatId)
                .build();

        repository.save(reservation);

        Reservation actual = repository.getBySeatIdAndVisitorIdAndShowId(newSeatId + 1, newVisitorId, newShowId);
        Assertions.assertNull(actual);
    }

    @Test
    public void testNoBySeatIdAndInvalidVisitorIdAndShowId() {

        Long newSeatId = 10L;
        Long newVisitorId = 10L;
        Long newShowId = 10L;
        Reservation reservation = new Reservation().builder()
                .showId(newShowId)
                .visitorId(newVisitorId)
                .seatId(newSeatId)
                .build();

        repository.save(reservation);

        Reservation actual = repository.getBySeatIdAndVisitorIdAndShowId(newSeatId, newVisitorId + 1, newShowId);
        Assertions.assertNull(actual);
    }

    @Test
    public void testNoBySeatIdAndVisitorIdAndInvalidShowId() {

        Long newSeatId = 10L;
        Long newVisitorId = 10L;
        Long newShowId = 10L;
        Reservation reservation = new Reservation().builder()
                .showId(newShowId)
                .visitorId(newVisitorId)
                .seatId(newSeatId)
                .build();

        repository.save(reservation);

        Reservation actual = repository.getBySeatIdAndVisitorIdAndShowId(newSeatId, newVisitorId, newShowId + 1);
        Assertions.assertNull(actual);
    }

    @Test
    public void testGetAllByShowId() {
        List<Reservation> expected = new ArrayList<>();
        Long newSeatId = 10L;
        Long newVisitorId = 10L;
        Long newShowId = 10L;
        Reservation reservation1 = new Reservation().builder()
                .showId(newShowId)
                .visitorId(newVisitorId)
                .seatId(newSeatId)
                .build();

        Reservation reservation2 = new Reservation().builder()
                .showId(newShowId)
                .visitorId(newVisitorId + 1)
                .seatId(newSeatId + 1)
                .build();

        expected.addAll(Arrays.asList(reservation1, reservation2));
        repository.saveAll(expected);

        List<Reservation> actual = repository.getAllByShowId(newShowId);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testEmptyForGetAllByInvaliShowId() {
        Long invalidShowId = 10L;

        List<Reservation> actual = repository.getAllByShowId(invalidShowId);

        assertThat(actual).isEmpty();
    }

//    @Test
//    void deleteById() {
//    }
}