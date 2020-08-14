package com.drbsimon.cinema;

import com.drbsimon.cinema.entity.Room;
import com.drbsimon.cinema.entity.Seat;
import com.drbsimon.cinema.repository.RoomRepository;
import com.drbsimon.cinema.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@SpringBootApplication
public class CinemaApplication {
//    private RoomRepository roomRepository;
//    private SeatRepository seatRepository;

    public static void main(String[] args) {
        SpringApplication.run(CinemaApplication.class, args);
    }

    // Enable Swagger 2 documentation on all endpoints
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.ant("/**"))
                .build();
    }

    // TODO: replace to DataProvider
//    @Bean
//    @Profile("production")
//    public CommandLineRunner init() {
//        return args -> {
//            createCinema(1);
//        };
//    }
//
//    private void createCinema(int numberOfRooms) {
//        List<String> roomNames = Arrays.asList("Patkós Irma", "Huszárik Zoltán", "Uránia");
//        Random random = new Random();
//
//        List<Room> rooms = new ArrayList<>();
//        for (int roomId = 1; roomId < numberOfRooms+1; roomId++) {
//            int randomIndex = random.nextInt(roomNames.size());
//            String name = roomNames.get(randomIndex);
//            roomNames.remove(randomIndex);
//
//            Room newRoom = Room.builder()
//                    .name(name)
//                    .numberOfRows(5)
//                    .numberOfSeatsPerRow(9)
//                    .build();
//            newRoom.calculateCapacity();
//            createSeatsForRoomData(newRoom);
//            rooms.add(newRoom);
//        }
//        roomRepository.saveAll(rooms);
//    }
//
//    private void createSeatsForRoomData(Room room) {
//        for (int row = 1; row < room.getNumberOfRows() + 1; row++) {
//            for (int seatNumber = 1; seatNumber < room.getNumberOfSeatsPerRow() + 1; seatNumber++) {
//                Seat newSeat = Seat.builder()
//                        .rowNumber(row)
//                        .seatNumber(seatNumber)
//                        .room(room)
//                        .build();
//                seatRepository.save(newSeat);
//            }
//        }
//    }
}
