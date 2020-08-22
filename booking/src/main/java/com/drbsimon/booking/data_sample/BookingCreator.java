package com.drbsimon.booking.data_sample;

import com.drbsimon.booking.entity.Reservation;
import com.drbsimon.booking.model.*;
import com.drbsimon.booking.repository.ReservationRepository;
import com.drbsimon.booking.service.CatalogServiceCaller;
import com.drbsimon.booking.service.CinemaServiceCaller;
import com.drbsimon.booking.service.VisitorServiceCaller;
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
        List<Show> shows = catalogServiceCaller.getAllShows();
        List<Visitor> visitors = visitorServiceCaller.getAllVisitors();
        List<Visitor> users = visitors.stream()
                .filter(visitor -> visitor.getRoles().contains(Role.ROLE_USER))
                .collect(Collectors.toList());
        Visitor loneUser = users.get(0);

        for (Show show : shows) {
            Room room = cinemaServiceCaller.getRoomById(show.getRoomId());

            for (Seat seat : room.getSeats()) {
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
