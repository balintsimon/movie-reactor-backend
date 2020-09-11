package com.drbsimon.cinema;

import com.drbsimon.cinema.entity.Room;
import com.drbsimon.cinema.entity.Seat;
import com.drbsimon.cinema.model.RoomListWrapper;
import com.drbsimon.cinema.repository.RoomManager;
import com.drbsimon.cinema.repository.RoomRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@ComponentScan(basePackages = {"com.drbsimon.cinema"})
class RoomTests {

    @Autowired
    private RoomRepository repository;

    @Autowired
    private RoomManager manager;

    @Test
    public void testRepositoryLoads() throws Exception {
        assertThat(repository).isNotNull();
    }

    @Test
    public void testManagerLoads() throws Exception {
        assertThat(manager).isNotNull();
    }

    @Test
    public void testSaveOneRoomSimple() {
        Room room = Room.builder()
                .name("1")
                .numberOfRows(1)
                .numberOfSeatsPerRow(1)
                .seats(null)
                .build();

        repository.save(room);

        List<Room> rooms = repository.findAll();
        assertThat(rooms).hasSize(1);
    }

    @Test
    void testSaveRoomWithInvalidRowNumber() {
        Room room = Room.builder()
                .name("1")
                .numberOfRows(-1)
                .numberOfSeatsPerRow(1)
                .seats(null)
                .build();
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            repository.saveAndFlush(room);
        });
    }

    @Test
    public void testSaveSeveralRoomSimple() {
        Room room1 = Room.builder()
                .name("1")
                .numberOfRows(1)
                .numberOfSeatsPerRow(1)
                .seats(null)
                .build();

        Room room2 = Room.builder()
                .name("2")
                .numberOfRows(1)
                .numberOfSeatsPerRow(1)
                .seats(null)
                .build();

        Room room3 = Room.builder()
                .name("3")
                .numberOfRows(1)
                .numberOfSeatsPerRow(1)
                .seats(null)
                .build();
        repository.saveAll(Arrays.asList(room1, room2, room3));

        List<Room> rooms = repository.findAll();
        assertThat(rooms).hasSize(3);
    }

    @Test
    public void testFindRoomById() {
        Room room1 = Room.builder()
                .name("1")
                .numberOfRows(1)
                .numberOfSeatsPerRow(1)
                .seats(null)
                .build();

        Room room2 = Room.builder()
                .name("2")
                .numberOfRows(1)
                .numberOfSeatsPerRow(1)
                .seats(null)
                .build();

        Room room3 = Room.builder()
                .name("3")
                .numberOfRows(1)
                .numberOfSeatsPerRow(1)
                .seats(null)
                .build();
        repository.saveAll(Arrays.asList(room1, room2, room3));

        List<Room> rooms = repository.findAll();

        Room expectedResult = rooms.get(0);
        Long sampleId = expectedResult.getId();
        Room actualResult = repository.getById(sampleId);

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    public void testNoRoomNameThrowsError() {
        Room room = Room.builder()
                .numberOfRows(1)
                .numberOfSeatsPerRow(1)
                .seats(null)
                .build();

        Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            repository.save(room);
            repository.findAll();
        });
    }

    @Test
    public void testManagerForOne() {
        Room room1 = Room.builder()
                .name("1")
                .numberOfRows(1)
                .numberOfSeatsPerRow(1)
                .seats(null)
                .build();

        Room room2 = Room.builder()
                .name("2")
                .numberOfRows(1)
                .numberOfSeatsPerRow(1)
                .seats(null)
                .build();

        Room room3 = Room.builder()
                .name("3")
                .numberOfRows(1)
                .numberOfSeatsPerRow(1)
                .seats(null)
                .build();
        repository.saveAll(Arrays.asList(room1, room2, room3));

        List<Room> rooms = repository.findAll();

        Room expectedResult = rooms.get(0);
        Long sampleId = expectedResult.getId();
        Room actualResult = manager.getRoomById(sampleId);

        assertThat(actualResult).isEqualTo(expectedResult);
    }

    @Test
    public void testManagerForList() {
        Room room1 = Room.builder()
                .name("1")
                .numberOfRows(1)
                .numberOfSeatsPerRow(1)
                .seats(null)
                .build();

        Room room2 = Room.builder()
                .name("2")
                .numberOfRows(1)
                .numberOfSeatsPerRow(1)
                .seats(null)
                .build();

        Room room3 = Room.builder()
                .name("3")
                .numberOfRows(1)
                .numberOfSeatsPerRow(1)
                .seats(null)
                .build();
        repository.saveAll(Arrays.asList(room1, room2, room3));

        List<Room>  expectedResult = repository.findAll();
        RoomListWrapper wrapper = manager.getAllRooms();
        List<Room> actualResult = wrapper.getRooms();

        assertThat(actualResult).isEqualTo(expectedResult);
    }

}
