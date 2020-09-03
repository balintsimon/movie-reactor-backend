package com.drbsimon.booking.service;

import com.drbsimon.booking.entity.Reservation;
import com.drbsimon.booking.entity.SeatReservedWrapper;
import com.drbsimon.booking.model.AllBookingInfoWrapper;
import com.drbsimon.booking.model.ReservationWrapper;
import com.drbsimon.booking.repository.ReservationRepository;
import com.drbsimon.booking.service.caller.CatalogServiceCaller;
import com.drbsimon.booking.service.caller.CinemaServiceCaller;
import com.drbsimon.booking.service.caller.VisitorServiceCaller;
import com.drbsimon.booking.service.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ReservationOrganizer {
    private final ReservationRepository reservationRepository;
    private final CatalogServiceCaller catalogServiceCaller;
    private final CinemaServiceCaller cinemaServiceCaller;
    private final VisitorServiceCaller visitorServiceCaller;

    public boolean saveReservedSeats(SeatReservedWrapper reservationInfo)
            throws IllegalStateException {
        Long showId = reservationInfo.getId();
        List<Long> seatIds = reservationInfo.getSeats();

        if (seatIds.size() == 0) {
            return false;
        }

        if (!catalogServiceCaller.isShowValid(showId)) {
            throw new IllegalStateException("Show not found.");
        }

        for (Long actualSeatId : seatIds) {
            if (!cinemaServiceCaller.isSeatValid(actualSeatId)) {
                throw new IllegalStateException("Seat not found.");
            }

            Reservation foundSeat = reservationRepository.getBySeatIdAndShowId(actualSeatId, showId);
            if (foundSeat != null) {
                return false;
                // NB: throwing exception crashes frontend unbeknownst to the user.
                // It would be a bad user experience
//                    throw new IllegalStateException("Seat has been already taken.");
            }
        }

        for (Long actualSeatId : seatIds) {
            Reservation newReservation = Reservation.builder()
                    .seatId(actualSeatId)
                    .showId(showId)
                    .visitorId(reservationInfo.getVisitorId())
                    .build();
            reservationRepository.save(newReservation);
        }
        return true;
    }

    public boolean deleteReservation(SeatReservedWrapper reservationInfo) {
        Long seatId = reservationInfo.getSeats().size() == 1 ? reservationInfo.getSeats().get(0) : null;
        Long visitorId = reservationInfo.getVisitorId();
        if (seatId != null) {
            Reservation reservation = reservationRepository.getBySeatIdAndVisitorId(seatId, visitorId);
            if (reservation != null) {
                reservationRepository.deleteById(seatId);
                return true;
            }
        }
        return false;
    }

    public ReservationWrapper getAllReservations() {
        return new ReservationWrapper(reservationRepository.findAll());
    }

    public ReservationWrapper getAllReservationsOfUserWithExtraInfo(Long visitorId) {
        return new ReservationWrapper(reservationRepository.getAllByVisitorId(visitorId));
    }
    
    public AllBookingInfoWrapper getReservationsFactory() {
        //TODO: create endpoint to get visitor from JWT OR send information in header through gateway!!!
        Visitor visitor = new Visitor(); //customUserDetailsService.getUserFromToken();
        System.out.println(visitor.getUsername());
        if (visitor.getRoles().contains(Role.ROLE_ADMIN)) return getAllReservationsWithExtraInfo();
        else if (visitor.getRoles().contains(Role.ROLE_USER)) return getAllReservationsOfUserWithExtraInfo(visitor);
        return null;
    }

    private AllBookingInfoWrapper getAllReservationsWithExtraInfo() {
        List<Reservation> reservations = reservationRepository.findAll();
        List<AllBookingInfo> allInformation = new ArrayList<>();
        for (Reservation reservation : reservations) {
            Show show = catalogServiceCaller.getShowById(reservation.getShowId());
            Visitor visitor = new Visitor(); //visitorRepository.getById(reservation.getVisitorId());
            Seat seat = cinemaServiceCaller.getSeatById(reservation.getSeatId());
            allInformation.add(new AllBookingInfo(reservation.getId(), visitor, seat, show, show.getMovieDbId()));
        }
        return new AllBookingInfoWrapper(allInformation);
    }

    private AllBookingInfoWrapper getAllReservationsOfUserWithExtraInfo(Visitor visitor) {
        List<Reservation> reservations = reservationRepository.getAllByVisitorId(visitor.getId());
        List<AllBookingInfo> allInformation = new ArrayList<>();
        for (Reservation reservation : reservations) {
            Show show = catalogServiceCaller.getShowById(reservation.getShowId());
            Seat seat = cinemaServiceCaller.getSeatById(reservation.getSeatId());
            allInformation.add(new AllBookingInfo(reservation.getId(), visitor, seat, show, show.getMovieDbId()));
        }
        return new AllBookingInfoWrapper(allInformation);
    }
}
