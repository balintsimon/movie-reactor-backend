package com.drbsimon.booking.data_sample;

import com.drbsimon.booking.entity.Reservation;
import com.drbsimon.booking.model.Role;
import com.drbsimon.booking.model.dto.RoomDTO;
import com.drbsimon.booking.model.dto.SeatDTO;
import com.drbsimon.booking.model.dto.ShowDTO;
import com.drbsimon.booking.model.dto.VisitorDTO;
import com.drbsimon.booking.repository.ReservationRepository;
import com.drbsimon.booking.service.caller.CatalogServiceCaller;
import com.drbsimon.booking.service.caller.CinemaServiceCaller;
import com.drbsimon.booking.service.caller.VisitorServiceCaller;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class BookingCreator {

    private final ReservationRepository reservationRepository;
    private final CatalogServiceCaller catalogServiceCaller;
    private final CinemaServiceCaller cinemaServiceCaller;
    private final VisitorServiceCaller visitorServiceCaller;
    private final Random random = new Random();

    public BookingCreator(ReservationRepository reservationRepository,
                          CatalogServiceCaller catalogServiceCaller,
                          CinemaServiceCaller cinemaServiceCaller,
                          VisitorServiceCaller visitorServiceCaller) {
        this.reservationRepository = reservationRepository;
        this.catalogServiceCaller = catalogServiceCaller;
        this.cinemaServiceCaller = cinemaServiceCaller;
        this.visitorServiceCaller = visitorServiceCaller;
    }

    public void createBookings() {
        List<ShowDTO> shows = catalogServiceCaller.getAllShows();
        List<VisitorDTO> visitorDTOS = visitorServiceCaller.getAllVisitors();
        List<VisitorDTO> users = visitorDTOS.stream()
                .filter(visitor -> visitor.getRoles().contains(Role.ROLE_USER))
                .collect(Collectors.toList());
        VisitorDTO loneUser = users.get(0);

        for (ShowDTO show : shows) {
            RoomDTO room = cinemaServiceCaller.getRoomById(show.getRoomId());

            for (SeatDTO seat : room.getSeats()) {
                if (random.nextBoolean()) {
                    Reservation newReservation = Reservation.builder()
                            .visitorId(loneUser.getId())
                            .showId(show.getId())
                            .seatId(seat.getId())
                            .build();
                    reservationRepository.save(newReservation);
                }
            }
        }
    }
}
